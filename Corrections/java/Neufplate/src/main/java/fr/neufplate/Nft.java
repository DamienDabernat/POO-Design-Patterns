package fr.neufplate;

import fr.neufplate.PriceCalculator.ExcludingTaxesPriceDecorator;
import fr.neufplate.PriceCalculator.IncludingTaxesPriceDecorator;
import fr.neufplate.PriceCalculator.NftPrice;
import fr.neufplate.PriceCalculator.Price;
import fr.neufplate.User.User;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Nft {
    public String title;
    private String hash;
    public Avatar avatar;
    public Long nonce;

    public int level;

    private long startTime;
    private long endTime;

    public Nft() {
        this.startTime = System.currentTimeMillis();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
        this.endTime = System.currentTimeMillis();
    }

    public double getPrice() {
        Price costPrice = new NftPrice(this.startTime, this.endTime);
        costPrice.calculate();
        //System.out.println("Cost price: " + costPrice.calculate());

        Price exclTax = new ExcludingTaxesPriceDecorator(costPrice);
        exclTax.calculate();
        //System.out.println("Price without taxes: " + costPrice.calculate());

        Price inclTax = new IncludingTaxesPriceDecorator(exclTax);
        inclTax.calculate();
        //System.out.println("Price with taxes: " + costPrice.calculate());

        return inclTax.baseValue;
    }

    public String getFormattedPrice(){
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(this.getPrice());
    }

    @Override
    public String toString() {
        return "\nNft \n" +
                "title : " + title + " \n" +
                "hash : " + hash + " \n" +
                "avatar : " + avatar + " \n" +
                "nonce : " + nonce + " \n" +
                "\n";
    }
}
