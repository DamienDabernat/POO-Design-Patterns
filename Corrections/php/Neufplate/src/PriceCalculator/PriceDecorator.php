<?php

namespace App\PriceCalculator;

class PriceDecorator extends Price
{

    public Price $price;

    public function __construct(Price $price)
    {
        $this->price = $price;
    }

    public function calculate(): float
    {
        return $this->price->calculate();
    }
}