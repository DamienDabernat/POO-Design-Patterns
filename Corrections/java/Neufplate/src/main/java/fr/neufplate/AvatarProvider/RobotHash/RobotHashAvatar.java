package fr.neufplate.AvatarProvider.RobotHash;

import fr.neufplate.Avatar;
import fr.neufplate.AvatarClientInterface;

public class RobotHashAvatar extends Avatar {

    @Override
    protected AvatarClientInterface getClient() {
        return RobotHashClient.getInstance();
    }
}
