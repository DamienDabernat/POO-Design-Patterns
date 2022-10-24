<?php

namespace App;

use App\PriceCalculator\ExcludingTaxesPriceDecorator;
use App\PriceCalculator\IncludingTaxesPriceDecorator;
use App\PriceCalculator\NftPrice;

class Nft
{

    public string $title;
    private string $hash;
    public Avatar $avatar;
    private int $startTime;
    private int $endTime;

    public function getHash(): string
    {
        return $this->hash;
    }


    public function setHash(string $hash): void
    {
        $this->hash = $hash;
        $this->endTime = time();
    }

    public function getPrice(): float
    {
        $costPrice = new NftPrice($this->startTime, $this->endTime);
        $costPrice->calculate();
        //echo $costPrice->baseValue . PHP_EOL;

        $exclTax = new ExcludingTaxesPriceDecorator($costPrice);
        $exclTax->calculate();
        //echo $exclTax->baseValue . PHP_EOL;

        $inclTax = new IncludingTaxesPriceDecorator($exclTax);
        $inclTax->calculate();
        //echo $inclTax->baseValue . PHP_EOL;

        return $inclTax->baseValue;
    }

    public function __construct()
    {
        $this->startTime = time();
    }

    public function __toString(): string
    {
        return $this->title
            . ' ' . $this->hash
            . ' ' . $this->avatar->url;
    }


}