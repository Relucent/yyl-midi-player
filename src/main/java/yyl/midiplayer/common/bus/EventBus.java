package yyl.midiplayer.common.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件总线类
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public enum EventBus {

    /** 事件总线实例 */
    INSTANCE;

    /** 事件监听器列表 */
    private final Map<EventType, List<EventListener>> eventListeners = new ConcurrentHashMap<>();

    /**
     * 订阅事件
     * @param <T>事件数据的类型
     * @param eventType 事件类型
     * @param listener 监听器（用于处理事件）
     */
    public synchronized <T> void subscribe(EventType<T> eventType, EventListener<T> listener) {
        eventListeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    /**
     * 取消订阅事件
     * @param <T> 事件数据的类型
     * @param eventType 事件类型
     * @param listener 监听器（用于处理事件）
     */
    public synchronized <T> void unsubscribe(EventType<T> eventType, EventListener<T> listener) {
        List<EventListener> listeners = eventListeners.get(eventType);
        if (listeners != null) {
            listeners.remove(listener);
            eventListeners.put(eventType, listeners);
        }
    }

    /**
     * 发布事件
     * @param <T> 事件数据的类型
     * @param eventType 事件类型
     * @param eventData 事件数据
     */
    public synchronized <T> void publish(EventType<T> eventType, T eventData) {
        List<EventListener> listeners = eventListeners.get(eventType);
        if (listeners != null) {
            for (EventListener listener : listeners) {
                listener.onEvent(eventType, eventData);
            }
        }
    }
}
