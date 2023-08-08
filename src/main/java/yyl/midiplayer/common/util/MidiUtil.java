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

import yyl.midiplayer.common.constant.MidiMetaEventTypeConstant;

/**
 * MIDI 工具类
 */
public class MidiUtil {

    private final static Logger LOG = LoggerFactory.getLogger(MidiUtil.class);

    /** 工具类，不需要实例化 */
    protected MidiUtil() {

    }

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
     * 获取MIDI的轨道名称
     * @param sequence MIDI序列
     * @return MIDI序列的信息
     */
    public static String[] getTrackNames(Sequence sequence) {
        Track[] tracks = sequence.getTracks();
        String[] trackNames = new String[tracks.length];
        for (int i = 0; i < tracks.length; i++) {
            Track track = tracks[i];
            for (int j = 0; j < track.size(); j++) {
                MidiEvent event = track.get(j);
                MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage) {
                    MetaMessage metaMessage = (MetaMessage) message;
                    // 序列/轨道名称，用于标识序列或轨道的名称
                    if (metaMessage.getType() == MidiMetaEventTypeConstant.TRACK_NAME) {
                        try {
                            trackNames[i] = new String(metaMessage.getData(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            // ignore
                        }
                    }
                }
            }
        }
        return trackNames;
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
