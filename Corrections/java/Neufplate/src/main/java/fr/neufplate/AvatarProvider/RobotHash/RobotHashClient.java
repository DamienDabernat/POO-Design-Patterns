package fr.neufplate.AvatarProvider.RobotHash;

import fr.neufplate.AvatarClientInterface;

public final class RobotHashClient implements AvatarClientInterface {

    private static RobotHashClient instance;

    private static final String apiUrl = "https://robohash.org";
    private static final String fileType = ".png";

    public static RobotHashClient getInstance() {
        if (instance == null) {
            instance = new RobotHashClient();
        }
        return instance;
    }

    public String getRandomAvatarUrl() {
        return apiUrl + "/" + Math.random() + fileType;
    }

    @Override
    public String getAvatarFromHash(String hash) {
        return apiUrl + "/" + hash + fileType;
    }


}