package yyl.midiplayer.controller;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.sound.midi.Sequence;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import yyl.midiplayer.common.bus.EventBus;
import yyl.midiplayer.common.bus.EventListener;
import yyl.midiplayer.common.bus.EventType;
import yyl.midiplayer.common.constant.MidiGuiConstant;
import yyl.midiplayer.common.util.MidiUtil;
import yyl.midiplayer.model.MusicInfo;
import yyl.midiplayer.model.MusicPlayer;
import yyl.midiplayer.model.PlayStatus;
import yyl.midiplayer.ui.MainFrame;
import yyl.midiplayer.ui.MusicControlPanel;
import yyl.midiplayer.ui.MusicListPanel;

/**
 * 控制器
 */
public class MusicPlayerController {

    private final MainFrame frame;
    private final MusicPlayer player;

    /**
     * 构造函数
     * @param frame 主窗口
     * @param player MIDI音乐播放器
     */
    public MusicPlayerController(MainFrame frame, MusicPlayer player) {
        this.frame = frame;
        this.player = player;
        initEventHandling();
    }

    /**
     * 初始化事件处理逻辑
     */
    private void initEventHandling() {
        EventBus eventBus = EventBus.INSTANCE;

        MusicControlPanel controlPanel = frame.getControlPanel();
        MusicListPanel musicListPanel = frame.getMusicListPanel();

        // 音量
        player.setVolumeRatio(controlPanel.getVolumeRatio());

        // 打开文件
        eventBus.subscribe(EventType.CLICK_OPEN, wrapInvokeLater((type, nil) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(MidiGuiConstant.MIDI_FILE_FILTER);
            int returnVal = chooser.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = chooser.getSelectedFiles();
                for (File file : selectedFiles) {
                    MusicInfo info = getMusicInfo(file);
                    musicListPanel.addMusicInfo(info);
                }
            }

        }));
        // 关闭应用
        eventBus.subscribe(EventType.CLICK_EXIT, wrapInvokeLater((type, nil) -> {
            System.exit(0);
        }));

        // 停止播放
        eventBus.subscribe(EventType.CLICK_STOP, wrapInvokeLater((type, nil) -> {
            player.stop();
        }));

        // 开始播放
        eventBus.subscribe(EventType.CLICK_PLAY, wrapInvokeLater((type, nil) -> {
            Sequence sequence = player.getSequence();
            if (sequence == null) {
                player.setSequence(getActivedSequence());
            }
            player.play();

        }));
        // 暂停播放
        eventBus.subscribe(EventType.CLICK_PAUSE, wrapInvokeLater((type, nil) -> {
            player.pause();
        }));

        // 开始播放之后
        eventBus.subscribe(EventType.PLAY_START_AFTER, wrapInvokeLater((type, nil) -> {
            musicListPanel.setPlayStatus(PlayStatus.PLAYING);
        }));
        // 暂停播放之后
        eventBus.subscribe(EventType.PLAY_STOP_AFTER, wrapInvokeLater((type, nil) -> {
            musicListPanel.setPlayStatus(PlayStatus.STOPPED);
            controlPanel.setProgressValue(player.getProgress());
        }));

        // 更改播放进度（用户点击主动更改播放进度）
        eventBus.subscribe(EventType.CLICK_UPDATE_PROGRESS, wrapInvokeLater((type, progress) -> {
            player.setProgress(progress);
        }));
        // 音乐音量控制
        eventBus.subscribe(EventType.CLICK_UPDATE_VOLUME_RATIO, wrapInvokeLater((type, ratio) -> {
            player.setVolumeRatio(ratio);
        }));
        // 音乐的播放进度
        eventBus.subscribe(EventType.PROGRESS_CHANGE, wrapInvokeLater((type, progress) -> {
            controlPanel.setProgressValue(progress);
        }));
        // 音乐播放完成
        eventBus.subscribe(EventType.END_OF_TRACK, wrapInvokeLater((type, progress) -> {
            int rowCount = musicListPanel.getRowCount();
            int rowIndex = musicListPanel.getActivedRowIndex();
            if (rowCount == 0) {
                player.stop();
            } else {
                musicListPanel.setActivedRowIndex((rowIndex + 1) % rowCount);
                player.setSequence(getActivedSequence());
                player.play();
            }
        }));
        // 更改激活的音乐 (通过双击列表)
        eventBus.subscribe(EventType.DBL_CLICK_ACTIVED_MUSIC, wrapInvokeLater((type, nil) -> {
            player.setSequence(getActivedSequence());
            player.play();
        }));

        // 处理拖拽文件事件
        eventBus.subscribe(EventType.DND_FILES, wrapInvokeLater((type, files) -> {
            for (File file : files) {
                MusicInfo info = getMusicInfo(file);
                musicListPanel.addMusicInfo(info);
            }
        }));

        // 处理键盘按键事件
        eventBus.subscribe(EventType.KEY_PRESSED, wrapInvokeLater((type, event) -> {
            // Delete键（键码为KeyEvent.VK_DELETE）
            if (event.getKeyCode() == KeyEvent.VK_DELETE) {
                int[] selectedRows = musicListPanel.getSelectedRows();
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    musicListPanel.removeMusicInfo(selectedRows[i]);
                }
            }
        }));
    }

    /**
     * 包装事件监听器，从而保证事件会在 Swing线程中运行
     * @param <T> 事件监听器处理的事件数据类型
     * @param listener 事件监听器
     * @return 包装后的事件监听器
     */
    private <T> EventListener<T> wrapInvokeLater(EventListener<T> listener) {
        return (EventListener<T>) (eventType, eventData) -> {
            SwingUtilities.invokeLater(() -> listener.onEvent(eventType, eventData));
        };
    }

    /**
     * 从文件读取音乐(MIDI)信息
     * @param file 文件
     * @return 音乐(MIDI)信息
     */
    private MusicInfo getMusicInfo(File file) {
        Sequence sequence = MidiUtil.getSequence(file);
        if (sequence == null) {
            return null;
        }
        return new MusicInfo(file, sequence);
    }

    /**
     * 获得当前获得的MIDI序列
     * @return MIDI序列
     */
    private Sequence getActivedSequence() {
        MusicListPanel musicListPanel = frame.getMusicListPanel();
        int rowIndex = musicListPanel.getActivedRowIndex();
        int rowCount = musicListPanel.getRowCount();
        rowIndex = Math.min(Math.max(0, rowIndex), rowCount - 1);
        musicListPanel.setActivedRowIndex(rowIndex);
        MusicInfo musicInfo = musicListPanel.getMusicInfo(rowIndex);
        if (musicInfo != null) {
            return musicInfo.getSequence();
        }
        return null;
    }
}
