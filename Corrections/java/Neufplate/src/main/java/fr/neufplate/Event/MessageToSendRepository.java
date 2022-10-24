package fr.neufplate.Event;

import fr.neufplate.Nft;
import fr.neufplate.User.User;

public class MessageToSendRepository {

    public static String sendOwnershipCertificate(User user, Nft nft) {
        return "\nHello " + user.firstName + " " + user.lastName + ",\n" +
                "Your ownership certificate is ready : " + "\n" +
                "Nonce : " + nft.nonce +"\n" +
                "Don't forget to keep it secret !" +
                "";
    }

    public static String sendNftCreated(User user, Nft nft) {
        return "\nHello " + user.firstName + " " + user.lastName + ",\n" +
                "Your Nft has just being created ! " + "\n" +
                "\nNft : " + nft.title + "" + "\n" +
                "Level : " +  "⭐️".repeat(nft.level) + "" + "\n" +
                "Avatar url " + nft.avatar.url + "\n" +
                "Hash : " + nft.getHash() + "\n" +
                "Value : " +  nft.getFormattedPrice() + "€\n" +
                "";
    }

    public static String sendDiscordWebhook(User user, Nft nft) {
        return "{\n" +
                "   \"username\":\"Webhook\",\n" +
                "   \"avatar_url\":\"https://i.imgur.com/4M34hi2.png\",\n" +
                "   \"content\":\"Voici un nouveau NFT\",\n" +
                "   \"embeds\":[\n" +
                "      {\n" +
                "         \"author\":{\n" +
                "            \"name\":\"Neufplate\",\n" +
                "            \"url\":\"https://www.reddit.com/r/cats/\",\n" +
                "            \"icon_url\":\"https://i.imgur.com/R66g1Pe.jpg\"\n" +
                "         },\n" +
                "         \"title\":\"" + nft.title +  "\",\n" +
                "         \"url\":\"https://google.com/\",\n" +
                "         \"description\":\" " + nft.getHash() + " \",\n" +
                "         \"color\":15258703,\n" +
                "         \"fields\":[\n" +
                "            {\n" +
                "               \"name\":\"Owner : \",\n" +
                "               \"value\":\"" + user.firstName + " " + user.lastName + "\",\n" +
                "               \"inline\":true\n" +
                "            },\n" +
                "            {\n" +
                "               \"name\":\"Level : \",\n" +
                "               \"value\":\" "+ nft.level + " \",\n" +
                "               \"inline\":true\n" +
                "            },\n" +
                "            {\n" +
                "               \"name\":\"Price : \",\n" +
                "               \"value\":\"" + nft.getFormattedPrice() + " euros\",\n" +
                "               \"inline\":true\n" +
                "            }\n" +
                "         ],\n" +
                "         \"image\":{\n" +
                "            \"url\":\"" + nft.avatar.url + "\"\n" +
                "         }\n" +
                "      }\n" +
                "   ]\n" +
                "}";
    }

}
