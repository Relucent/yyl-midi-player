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

    INSTANCE;

    private final Map<EventType, List<EventListener>> eventListeners = new ConcurrentHashMap<>();

    private EventBus() {
    }

    public synchronized <T> void subscribe(EventType<T> eventType, EventListener<T> listener) {
        eventListeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public synchronized <T> void unsubscribe(EventType<T> eventType, EventListener<T> listener) {
        List<EventListener> listeners = eventListeners.get(eventType);
        if (listeners != null) {
            listeners.remove(listener);
            eventListeners.put(eventType, listeners);
        }
    }

    public synchronized <T> void publish(EventType<T> eventType, T eventData) {
        List<EventListener> listeners = eventListeners.get(eventType);
        if (listeners != null) {
            for (EventListener listener : listeners) {
                listener.onEvent(eventType, eventData);
            }
        }
    }
}
