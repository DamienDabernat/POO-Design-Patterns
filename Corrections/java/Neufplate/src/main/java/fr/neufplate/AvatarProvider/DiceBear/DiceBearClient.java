package fr.neufplate.AvatarProvider.DiceBear;

import fr.neufplate.AvatarClientInterface;
import fr.neufplate.AvatarProvider.SpriteType;

public final class DiceBearClient implements AvatarClientInterface {

    private static DiceBearClient instance;

    public String spriteType = SpriteType.HUMAN.value;
    private static final String apiUrl = "https://avatars.dicebear.com/api/";
    private static final String fileType = ".svg";

    public static DiceBearClient getInstance() {
        if (instance == null) {
            instance = new DiceBearClient();
        }
        return instance;
    }

    public void setSpriteType(SpriteType spriteType) {
        this.spriteType = spriteType.value;
    }


    // Exercice 1
    public static String randomAvatarUrl() {
        return apiUrl + getInstance().spriteType + "/" + Math.random() + fileType;
    }

    // Exercice 2
    public String getRandomAvatarUrl() {
        return apiUrl + getInstance().spriteType + "/" + Math.random() + fileType;
    }

    @Override
    public String getAvatarFromHash(String hash) {
        return apiUrl + getInstance().spriteType + "/" + hash + fileType;
    }


}