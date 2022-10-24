<?php

namespace App\PriceCalculator;

class NftPrice extends Price
{

    private const computerPrice = 4000;
    private const screenTimeAverage = 5;
    private const computerLifetimeInYears = 3;

    private int $timeElapsedInSecond;

    public function __construct($startTime, $endTime)
    {
        $this->timeElapsedInSecond = $endTime - $startTime;
    }


    public function calculate(): float
    {
        $computerLifetimeInHour = self::computerLifetimeInYears * 365 * self::screenTimeAverage;
        $nftPricePerSecond = self::computerPrice / $computerLifetimeInHour / 60;
        $price = $nftPricePerSecond * $this->timeElapsedInSecond;
        $this->baseValue = $price;
        return $price;
    }
}