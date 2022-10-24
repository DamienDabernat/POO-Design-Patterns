package fr.neufplate.State;

import fr.neufplate.Avatar;
import fr.neufplate.AvatarProvider.DiceBear.DiceBearAvatar;
import fr.neufplate.Event.EventManager;
import fr.neufplate.Event.EventType;
import fr.neufplate.Event.MessageToSendRepository;
import fr.neufplate.Neufplate;
import fr.neufplate.AvatarProvider.RobotHash.RobotHashAvatar;
import fr.neufplate.AvatarProvider.SpriteType;

public class GeneratingState extends State {

    public GeneratingState(Neufplate neufplate) {
        super(neufplate);
    }

    @Override
    public String onTitling() {
        return null;
    }

    @Override
    public String onMakingCollision() {
        return null;
    }

    @Override
    public String onGenerating() {
        Avatar avatar = null;
        switch (this.neufplate.settings.providerType) {
            case DICEBEAR -> {
                SpriteType spriteType = this.neufplate.settings.spriteType;
                avatar = new DiceBearAvatar(spriteType);
            }
            case ROBOTHASH -> {
                avatar = new RobotHashAvatar();
            }
        }

        avatar.generate(this.neufplate.nft.getHash());
        this.neufplate.nft.avatar = avatar;

        this.neufplate.events.notify(EventType.GENERATED, this.neufplate.user,
                MessageToSendRepository.sendNftCreated(this.neufplate.user, this.neufplate.nft));

        this.neufplate.events.notify(EventType.DISCORD, this.neufplate.user,
                MessageToSendRepository.sendDiscordWebhook(this.neufplate.user, this.neufplate.nft));

        return avatar.url;
    }
}
