package fr.neufplate.AvatarProvider;

public enum SpriteType {
    MALE("male"),
    FEMALE("female"),
    HUMAN("human"),
    IDENTICON("identicon"),
    INITIALS("initials"),
    BOTTTS("bottts"),
    AVATAAARS("avataaars"),
    JDENTICON("jdenticon"),
    GRIDY("gridy"),
    MICAH("micah"),
    DEFAULT("default");

    public final String value;

    SpriteType(String value) {
        this.value = value;
    }
}