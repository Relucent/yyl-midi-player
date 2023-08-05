package yyl.midiplayer.common.bus;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;

/**
 * 事件类型
 */
@SuppressWarnings("serial")
public final class EventType<T> implements Serializable {
    /** 停止 */
    public static final EventType<Void> CLICK_STOP = new EventType<>();
    /** 播放 */
    public static final EventType<Void> CLICK_PLAY = new EventType<>();
    /** 暂停 */
    public static final EventType<Void> CLICK_PAUSE = new EventType<>();

    /** 打开文件 */
    public static final EventType<Void> CLICK_OPEN = new EventType<>();
    /** 关闭应用 */
    public static final EventType<Void> CLICK_EXIT = new EventType<>();

    /** 更改激活的音乐 (通过双击列表) */
    public static final EventType<Void> DBL_CLICK_ACTIVED_MUSIC = new EventType<>();

    /** 更改音量 */
    public static final EventType<Double> CLICK_UPDATE_VOLUME_RATIO = new EventType<>();
    /** 更改进度条 */
    public static final EventType<Double> CLICK_UPDATE_PROGRESS = new EventType<>();

    /** 拖拽文件事件 */
    public static final EventType<File[]> DND_FILES = new EventType<>();

    /** 全局键盘按键事件 */
    public static final EventType<KeyEvent> KEY_PRESSED = new EventType<>();

    /** 播放音乐 */
    public static final EventType<Void> PLAY_START_AFTER = new EventType<>();
    /** 停止音乐 */
    public static final EventType<Void> PLAY_STOP_AFTER = new EventType<>();

    /** 播放进度改变事件 */
    public static final EventType<Double> PROGRESS_CHANGE = new EventType<>();
    /** 播放结束 */
    public static final EventType<Void> END_OF_TRACK = new EventType<>();

    /** 错误消息事件 */
    public static final EventType<String> SHOW_ERROR_MESSAGE = new EventType<>();

    EventType() {

    }
}
