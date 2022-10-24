<?php

namespace App\PriceCalculator;

class ExcludingTaxesPriceDecorator extends PriceDecorator
{


    public function __construct(Price $price)
    {
        parent::__construct($price);
    }

    public function calculate(): float
    {
        $this->baseValue = parent::calculate() * 10;
        return $this->baseValue;
    }

}