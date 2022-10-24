package fr.neufplate.User;

import fr.neufplate.Avatar;
import fr.neufplate.Nft;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String firstName;
    public String lastName;
    public List<Nft> nftList = new ArrayList<>();
    public String phone;
    public String address;
    public String email;

    @Override
    public String toString() {
        return "User \n" +
                "firstName : " + firstName + "'\n" +
                "lastName : " + lastName + "'\n" +
                "phone : " + phone + "'\n" +
                "address : " + address + "'\n" +
                "email : " + email + "'\n" +
                "\nnftList : \n" + printNftList();
    }

    public String printNftList() {
        StringBuilder toString = new StringBuilder();
        for (Nft nft : nftList) {
            toString.append(nft.toString()).append("\n");
        }
        return toString.toString();
    }
}