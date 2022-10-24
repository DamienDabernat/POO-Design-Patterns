package fr.neufplate.Event;

import fr.neufplate.Nft;
import fr.neufplate.User.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager(EventType... eventTypes) {
        for (EventType oneEvent : eventTypes) {
            this.listeners.put(oneEvent.name(), new ArrayList<>());
        }
    }

    public void subscribe(EventType eventType, EventListener listener) {
        List<EventListener> listOfListener = listeners.get(eventType.name());
        listOfListener.add(listener);
    }

    public void unsubscribe(EventType eventType, EventListener listener) {
        List<EventListener> listOfListener = listeners.get(eventType.name());
        listOfListener.remove(listener);
    }

    public void unsubscribeFromAllListener(EventType... eventType) {
        for (EventType oneEvent : eventType) {
            List<EventListener> listOfListener = listeners.get(oneEvent.name());
            listOfListener.clear();
        }
    }

    public void notify(EventType eventType, User user, String message) {
        List<EventListener> listenersForOneEvent = listeners.get(eventType.name());
        for (EventListener listener : listenersForOneEvent) {
            listener.update(eventType, user, message);
        }
    }
}