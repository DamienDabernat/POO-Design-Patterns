package fr.neufplate.AvatarProvider.DiceBear;

import fr.neufplate.Avatar;
import fr.neufplate.AvatarClientInterface;
import fr.neufplate.AvatarProvider.SpriteType;

public class DiceBearAvatar extends Avatar {

    private final SpriteType spriteType;

    public DiceBearAvatar(SpriteType spriteType) {
        if (spriteType == null) {
            throw new IllegalArgumentException("SpriteType cannot be null");
        }
        this.spriteType = spriteType;
    }

    @Override
    protected AvatarClientInterface getClient() {
        DiceBearClient client = DiceBearClient.getInstance();
        client.setSpriteType(this.spriteType);
        return client;
    }
}
