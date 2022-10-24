package fr.neufplate.AvatarProvider;

public class Settings {

    public ProviderType providerType;
    public SpriteType spriteType;

    public int level;

    public Settings(ProviderType providerType, SpriteType spriteType, int level) {
        this.providerType = providerType;
        this.spriteType = spriteType;
        this.level = level;
    }
}
