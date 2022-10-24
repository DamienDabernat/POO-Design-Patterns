package fr.neufplate.Event;

import fr.neufplate.Nft;
import fr.neufplate.User.User;

public class EmailNotificationListener implements EventListener {

    @Override
    public void update(EventType eventType, User user, String message) {
        System.out.println("\nEmail to " + user.email + " : \n" + message);
    }
}
