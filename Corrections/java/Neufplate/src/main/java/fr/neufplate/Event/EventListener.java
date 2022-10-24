package fr.neufplate.Event;

import fr.neufplate.Nft;
import fr.neufplate.User.User;

public interface EventListener {
    void update(EventType eventType, User user, String message);
}