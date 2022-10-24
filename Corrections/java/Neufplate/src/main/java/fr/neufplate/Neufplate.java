package fr.neufplate;

import fr.neufplate.AvatarProvider.Settings;
import fr.neufplate.Event.*;
import fr.neufplate.State.State;
import fr.neufplate.State.TitlingState;
import fr.neufplate.User.User;

public class Neufplate {


    private State state;
    public Nft nft;
    public Settings settings;
    public User user;

    public EventManager events = new EventManager(EventType.GENERATED, EventType.COLLIDED, EventType.DISCORD);

    public Nft process(User forUser, Settings withSettings) {
        this.nft = new Nft();
        this.user = forUser;
        this.settings = withSettings;
        this.nft.level = this.settings.level;
        subscribe(this.user);

        this.state = new TitlingState(this);
        this.state.onTitling();
        this.state.onMakingCollision();
        this.state.onGenerating();

        this.user.nftList.add(this.nft);
        this.events.unsubscribeFromAllListener(EventType.GENERATED, EventType.COLLIDED, EventType.DISCORD);
        return this.nft;
    }

    public void subscribe(User user) {
        if(user.email != null) {
            this.events.subscribe(EventType.COLLIDED, new EmailNotificationListener());
            this.events.subscribe(EventType.GENERATED, new EmailNotificationListener());
        }

        if(user.phone != null) {
            this.events.subscribe(EventType.COLLIDED, new SmsNotificationListener());
            this.events.subscribe(EventType.GENERATED, new SmsNotificationListener());
        }
        this.events.subscribe(EventType.GENERATED, new DiscordNotificationListener());
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }
}
