package yyl.midiplayer.common.bus;

/**
 * 事件监听器接口
 */
@FunctionalInterface
public interface EventListener<T> {
    /**
     * 触发事件
     * @param eventType 事件类型
     * @param eventData 事件数据
     */
    void onEvent(EventType<T> eventType, T eventData);
}