package yyl.midiplayer.model;

import javax.sound.midi.Sequence;

public class SequenceInfo {

    /** MIDI序列 */
    private final Sequence sequence;
    /** 序列/轨道名称 */
    private final String trackName;
    /** 分辨率 */
    private final int resolution;
    /** 序列的总时长（单位：微秒） */
    private final long totalMicroseconds;

    public SequenceInfo(Sequence sequence, String trackName, int resolution, long totalMicroseconds) {
        this.sequence = sequence;
        this.trackName = trackName;
        this.resolution = sequence.getResolution();
        this.totalMicroseconds = sequence.getMicrosecondLength();
    }

    public Sequence getSequence() {
        return sequence;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getResolution() {
        return resolution;
    }

    public long getTotalMicroseconds() {
        return totalMicroseconds;
    }
}
