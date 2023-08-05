package yyl.midiplayer.common.util;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yyl.midiplayer.Main;
import yyl.midiplayer.common.constant.MidiMetaEventTypeConstant;
import yyl.midiplayer.model.SequenceInfo;

public class MidiUtil {

    private final static Logger LOG = LoggerFactory.getLogger(Main.class);

    /**
     * 获得MIDI序列的信息，如果不是一个MIDI文件，则返回null
     * @param file MIDI文件
     * @return MIDI序列的信息
     */
    public static Sequence getSequence(File file) {
        if (file.canRead()) {
            try {
                return MidiSystem.getSequence(file);
            } catch (InvalidMidiDataException e) {
                LOG.error(file + " -> " + e.getMessage());
            } catch (Exception e) {
                LOG.error("!", e);
            }
        }
        return null;
    }

    /**
     * 获取MIDI的信息
     * @param file MIDI文件
     * @return MIDI序列的信息
     */
    public static SequenceInfo getSequenceInfo(File file) {
        Sequence sequence = MidiUtil.getSequence(file);
        if (sequence == null) {
            return null;
        }
        return getSequenceInfo(sequence);
    }

    /**
     * 获取MIDI的信息
     * @param sequence MIDI序列
     * @return MIDI序列的信息
     */
    public static SequenceInfo getSequenceInfo(Sequence sequence) {

        // 获取所有轨道
        Track[] tracks = sequence.getTracks();

        String trackName = "";
        for (Track track : tracks) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) message;
                    // 序列/轨道名称，用于标识序列或轨道的名称
                    if (metaMessage.getType() == MidiMetaEventTypeConstant.TRACK_NAME) {
                        try {
                            trackName = new String(metaMessage.getData(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            // ignore
                        }
                    }
                }
            }
        }

        // 获取MIDI序列的分辨率（ticks per beat）
        int resolution = sequence.getResolution();
        // 获取MIDI序列的总时长（单位：微秒）
        long totalMicroseconds = sequence.getMicrosecondLength();

        return new SequenceInfo(sequence, trackName, resolution, totalMicroseconds);
    }

    /**
     * 格式化序列的时长
     * @param microseconds 序列的时长（毫秒）
     * @return 序列的时长字符串
     */
    public static String formatMicrosecondLength(long microseconds) {
        long seconds = microseconds / 1000000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return minutes + ":" + (remainingSeconds > 9 ? remainingSeconds : "0" + remainingSeconds);

    }
}
