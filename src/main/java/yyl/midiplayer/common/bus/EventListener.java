package yyl.midiplayer.common.bus;

/**
 * 事件监听器接口
 */
public interface EventListener<T> {
    void onEvent(EventType<T> eventType, T eventData);
}