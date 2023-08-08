package yyl.midiplayer.common.constant;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * GUI 相关常量
 */
public class MidiGuiConstant {

    /**
     * MIDI文件筛选器
     */
    public static final FileFilter MIDI_FILE_FILTER;
    static {
        MIDI_FILE_FILTER = new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                return file.isDirectory() //
                        || name.toLowerCase().endsWith(".mid") //
                        || name.toLowerCase().endsWith(".midi");
            }

            @Override
            public String getDescription() {
                return "MIDI Files (*.mid, *.midi)";
            }
        };
    }

    /** 常量类，不需要实例化 */
    private MidiGuiConstant() {
    }
}
