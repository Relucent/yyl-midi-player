package yyl.midiplayer.common.constant;

/**
 * MIDI控制器号常量类
 */
public class MidiControllerConstant {

    /** 1 (Modulation Wheel): 调制轮。通常用于控制合成器音色的声音变化和调制效果。 */
    public static final int MODULATION_WHEEL = 1;

    /** 7 (Volume): 音量控制器。用于控制音轨的音量大小。 */
    public static final int VOLUME = 7;

    /** 10 (Pan): 平衡控制器。用于控制音轨在立体声或环绕声环境中的平衡。 */
    public static final int PAN = 10;

    /** 11 (Expression): 表达力控制器。用于调整音轨的表现力和音色亮度。 */
    public static final int EXPRESSION = 11;

    /** 64 (Sustain Pedal): 踏板控制器。通常用于控制延音效果，按下踏板时音符会继续保持，释放时音符停止。 */
    public static final int SUSTAIN_PEDAL = 64;

    /** 91 (Reverb Send Level): 混响发送级别控制器。用于控制音轨发送到混响效果器的音量大小。 */
    public static final int REVERB_SEND_LEVEL = 91;

    /** 93 (Chorus Send Level): 合唱发送级别控制器。用于控制音轨发送到合唱效果器的音量大小。 */
    public static final int CHORUS_SEND_LEVEL = 93;

    /** 100 (RPN LSB): Registered Parameter Number Least Significant Byte。用于一些特殊的、设备相关的参数控制。 */
    public static final int RPN_LSB = 100;

    /** 101 (RPN MSB): Registered Parameter Number Most Significant Byte。用于一些特殊的、设备相关的参数控制。 */
    public static final int RPN_MSB = 101;

    /** 120 (All Sound Off): 停止所有音轨的声音。 */
    public static final int ALL_SOUND_OFF = 120;

    /** 121 (Reset All Controllers): 重置所有控制器到默认状态。 */
    public static final int RESET_ALL_CONTROLLERS = 121;

    /** 123 (All Notes Off): 停止所有音符的发声。 */
    public static final int ALL_NOTES_OFF = 123;

    /** 常量类，不需要实例化 */
    private MidiControllerConstant() {
    }
}
