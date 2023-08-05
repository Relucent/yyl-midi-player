package yyl.midiplayer.model;

import java.io.File;

/**
 * 音乐信息
 */
public class MusicInfo {

    private File file;
    private String name;
    private SequenceInfo sequenceInfo;
    private long progress;

    public MusicInfo(File file, SequenceInfo sequenceInfo) {
        this.file = file;
        this.name = file.getName();
        this.sequenceInfo = sequenceInfo;
        this.progress = 0;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public SequenceInfo getSequenceInfo() {
        return sequenceInfo;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getProgress() {
        return progress;
    }
}
