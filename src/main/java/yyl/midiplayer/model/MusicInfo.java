package yyl.midiplayer.model;

import java.io.File;

import javax.sound.midi.Sequence;

/**
 * 音乐信息
 */
public class MusicInfo {

    private final File file;
    private final String name;
    private final Sequence sequence;
    private volatile long progress;

    public MusicInfo(File file, Sequence sequence) {
        this.file = file;
        this.name = file.getName();
        this.sequence = sequence;
        this.progress = 0;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getProgress() {
        return progress;
    }
}
