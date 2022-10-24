package fr.neufplate.PriceCalculator;

import fr.neufplate.Nft;

public class NftPrice extends Price {

    private final int computerPrice = 4000;
    private final int screenTimeAverageByDay = 5;
    private final int computerLifetimeInYears = 3;
    private final double timeElapsedInSecond;

    public NftPrice(long startTime, long endTime) {
        float timeElapsedInMilliSecond = endTime - startTime;
        //System.out.println("timeElapsedInMilliSecond = " + timeElapsedInMilliSecond);

        this.timeElapsedInSecond =  timeElapsedInMilliSecond / 1000;
        //System.out.println("timeElapsedInSecond = " + timeElapsedInSecond);
    }

    @Override
    public double calculate() {
        int computerLifetime = screenTimeAverageByDay * 365 * computerLifetimeInYears * 60;
        double nftPricePerMinute = (double) computerPrice / computerLifetime;
        double nftPricePerSecond = nftPricePerMinute / 60;
        double price = nftPricePerSecond * timeElapsedInSecond;
        this.baseValue = price;
        return price;
    }
}
