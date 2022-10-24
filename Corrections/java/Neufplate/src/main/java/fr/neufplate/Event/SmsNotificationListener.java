package fr.neufplate.Event;

import fr.neufplate.Nft;
import fr.neufplate.User.User;

public class SmsNotificationListener implements EventListener {

    @Override
    public void update(EventType eventType, User user, String message) {
        System.out.println("\nSMS to " + user.phone + " : \n" + message);
    }
}
