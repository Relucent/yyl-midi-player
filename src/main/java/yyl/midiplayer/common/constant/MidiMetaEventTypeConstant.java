package yyl.midiplayer.common.constant;

/**
 * MIDI元事件类型定义
 */
public class MidiMetaEventTypeConstant {

    /** 示MIDI文件的序列号，用于标识不同的MIDI文件 */
    public static final int SEQUENCE_NUMBER = 0x00; //
    /** 文本事件，用于存储文本信息，如曲名、作曲者等 */
    public static final int TEXT_EVENT = 0x01; //
    /** 版权声明，用于标识音乐的版权信息 */
    public static final int COPYRIGHT_NOTICE = 0x02; //
    /** 序列/轨道名称，用于标识序列或轨道的名称 */
    public static final int TRACK_NAME = 0x03; //
    /** 乐器名称，用于标识乐器的名称 */
    public static final int INSTRUMENT_NAME = 0x04; //
    /** 歌词事件，用于存储歌曲的歌词信息 */
    public static final int LYRIC = 0x05; //
    /** 标记事件，用于在音乐中添加标记点 */
    public static final int MARKER = 0x06; //
    /** 提示事件，用于指示音乐的某个特定点 */
    public static final int CUE_POINT = 0x07; //
    /** MIDI通道前缀事件，用于指定后续MIDI事件的通道 */
    public static final int MIDI_CHANNEL_PREFIX = 0x20; //
    /** 音轨结束事件，用于表示音轨的结束 */
    public static final int END_OF_TRACK = 0x2F; //
    /** 设置拍子速度事件，用于设置MIDI音乐的速度(BPM) */
    public static final int TEMPO = 0x51; //
    /** SMPTE偏移事件，用于指定时间码的偏移 */
    public static final int SMPTE_OFFSET = 0x54; //
    /** 时间签名事件，用于设置节拍和拍号信息 */
    public static final int TIME_SIGNATURE = 0x58; //
    /** 调号事件，用于设置音乐的调式 */
    public static final int KEY_SIGNATURE = 0x59; //
    /** 序列器特定元事件，用于存储特定于序列器的信息 */
    public static final int SPECIFIC_META_EVENT = 0x7F; //

    /** 常量类，不需要实例化 */
    private MidiMetaEventTypeConstant() {
    }
}
