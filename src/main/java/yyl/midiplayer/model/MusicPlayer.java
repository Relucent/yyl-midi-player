package yyl.midiplayer.model;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yyl.midiplayer.Main;
import yyl.midiplayer.common.bus.EventBus;
import yyl.midiplayer.common.bus.EventType;
import yyl.midiplayer.common.constant.MidiMetaEventTypeConstant;

/**
 * MIDI音乐播放器
 */
public class MusicPlayer {

    private final static Logger LOG = LoggerFactory.getLogger(Main.class);

    private final static int CHANGE_VOLUME_CONTROLLER = 7;
    private final static int MAX_CONTROL_VALUE = 127;

    /** 获取MIDI合成器（用于将将MIDI事件转换为声音，并输出给音频设备进行播放） */
    private final Synthesizer synthesizer;
    /** 顺序器 (用于读取MIDI序列（Sequence）并控制其播放) */
    private final Sequencer sequencer;

    /**
     * MIDI音乐播放器
     */
    public MusicPlayer() {

        try {
            sequencer = MidiSystem.getSequencer(false);
            sequencer.open();

            if (sequencer instanceof Synthesizer) {
                synthesizer = (Synthesizer) sequencer;
            } else {
                synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();

                if (synthesizer.getDefaultSoundbank() == null) {
                    sequencer.getTransmitter().setReceiver(MidiSystem.getReceiver());
                } else {
                    sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
                }
            }

        } catch (Exception e) {
            showErrorMessage(e);
            throw new RuntimeException(e);
        }

        initListener();
    }

    /**
     * 初始化事件监听
     */
    private void initListener() {
        sequencer.addMetaEventListener(new MetaEventListener() {
            @Override
            public void meta(MetaMessage meta) {
                if (meta.getType() == MidiMetaEventTypeConstant.END_OF_TRACK) {
                    EventBus.INSTANCE.publish(EventType.END_OF_TRACK, null);
                }
            }
        });

        // 启动定时器，每隔500毫秒获取一次播放进度
        Thread timer = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    return;
                }
                if (sequencer.isRunning()) {
                    // 获取音乐总长度（单位微秒）
                    long total = sequencer.getMicrosecondLength();
                    long position = sequencer.getMicrosecondPosition();
                    double progress = position == 0 ? 0 : position == total ? 1 : ((double) position) / ((double) total);
                    EventBus.INSTANCE.publish(EventType.PROGRESS_CHANGE, progress);
                }
            }
        });
        timer.setDaemon(true);
        timer.start();
    }

    /**
     * 装载 MIDI序列
     * @param sequence MIDI序列
     */
    public void setSequence(Sequence sequence) {
        try {
            sequencer.setMicrosecondPosition(0);
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            LOG.error("!", e);
            showErrorMessage(e);
        }
    }

    /**
     * 获得 MIDI序列
     * @return MIDI序列
     */
    public Sequence getSequence() {
        return sequencer.getSequence();
    }

    /**
     * 播放音乐
     */
    public void play() {
        if (sequencer.isRunning()) {
            return;
        }
        try {
            sequencer.start();
            EventBus.INSTANCE.publish(EventType.PLAY_START_AFTER, null);
        } catch (Exception e) {
            LOG.error("!", e);
            showErrorMessage(e);
        }
    }

    /**
     * 暂停音乐
     */
    public void pause() {
        if (sequencer.isRunning()) {
            sequencer.stop();
            EventBus.INSTANCE.publish(EventType.PLAY_STOP_AFTER, null);
        } else {
            sequencer.start();
            EventBus.INSTANCE.publish(EventType.PLAY_START_AFTER, null);
        }
    }

    /**
     * 停止音乐
     */
    public void stop() {
        sequencer.stop();
        sequencer.setMicrosecondPosition(0);
        EventBus.INSTANCE.publish(EventType.PLAY_STOP_AFTER, null);
    }

    /**
     * 设置播放进度（取值范围0到1，0表示开始，1表示结束）
     * @param progress 播放进度
     */
    public void setProgress(double progress) {
        // 获取音乐总长度（单位微秒）
        long total = sequencer.getMicrosecondLength();
        // 计算播放位置（单位微秒）
        long position = (long) (total * progress);
        // 设置播放位置
        sequencer.setMicrosecondPosition(position);
    }

    /**
     * 获得播放进度（取值范围0到1，0表示开始，1表示结束）
     * @param progress 播放进度
     */
    public double getProgress() {
        // 获取音乐总长度（单位微秒）
        long total = sequencer.getMicrosecondLength();
        // 计算播放位置（单位微秒）
        long position = sequencer.getMicrosecondPosition();
        // 计算播放进度
        return total <= 0 ? 0 : ((double) position) / total;
    }

    /**
     * 设置音量（取值范围0到1，0表示无声音，1表示全音量）
     * @param ratio 音量
     */
    public void setVolumeRatio(double ratio) {
        setVolume((int) (MAX_CONTROL_VALUE * ratio));
    }

    /**
     * 设置音量 (数值范围 0~127，0是静音，127是全音量)
     * @param value 音量数值
     */
    private void setVolume(int value) {
        value = Math.max(0, Math.min(value, MAX_CONTROL_VALUE));
        MidiChannel[] channels = synthesizer.getChannels();
        for (int c = 0; c < channels.length; c++) {
            if (channels[c] != null) {
                channels[c].controlChange(CHANGE_VOLUME_CONTROLLER, value);
            }
        }
    }

    /**
     * 发送错误消息
     * @param e 异常
     */
    private void showErrorMessage(Exception e) {
        EventBus.INSTANCE.publish(EventType.SHOW_ERROR_MESSAGE, e.toString());
    }
}
