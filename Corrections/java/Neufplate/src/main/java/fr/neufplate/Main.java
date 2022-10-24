package fr.neufplate;

import fr.neufplate.AvatarProvider.DiceBear.DiceBearAvatar;
import fr.neufplate.AvatarProvider.DiceBear.DiceBearClient;
import fr.neufplate.AvatarProvider.Settings;
import fr.neufplate.AvatarProvider.ProviderType;
import fr.neufplate.AvatarProvider.RobotHash.RobotHashAvatar;
import fr.neufplate.AvatarProvider.SpriteType;
import fr.neufplate.PriceCalculator.ExcludingTaxesPriceDecorator;
import fr.neufplate.PriceCalculator.NftPrice;
import fr.neufplate.User.User;
import fr.neufplate.User.UserBuilder;

public class Main {
    public static void main(String[] args) {

        //--------
        // Exercise 3
        UserBuilder builder = new UserBuilder();

        User user = builder
                .addNames("Damien", "Dabernat")
                .addAddress("Fake address 1234")
                .addPhone("0685858585")
                .build();

        //-------
        // Exercise 4
        Settings settings = new Settings(ProviderType.ROBOTHASH, SpriteType.DEFAULT, 2);
        Neufplate neufplate = new Neufplate();
        Nft nft = neufplate.process(user, settings);
    }





    private static void previousExercises() {
        // ----------
        // Exercise 1
        //System.out.println(DiceBearClient.randomAvatarUrl());
        //System.out.println(DiceBearClient.randomAvatarUrl());

        // Version de base
        DiceBearClient.getInstance().spriteType = "avataaars";

        //Version de ouf
        DiceBearClient.getInstance().setSpriteType(SpriteType.AVATAAARS);

        //System.out.println(DiceBearClient.randomAvatarUrl());
        //System.out.println(DiceBearClient.randomAvatarUrl());

        // ----------
        // Exercise 2
        Avatar avatarDiceBear = new DiceBearAvatar(SpriteType.FEMALE);
        avatarDiceBear.generate();
        //System.out.println(avatarDiceBear.url);

        Avatar avatarRobotHash = new RobotHashAvatar();
        avatarRobotHash.generate();
        //System.out.println(avatarRobotHash.url);
    }



}