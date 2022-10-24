<?php

namespace App\PriceCalculator;

class IncludingTaxesPriceDecorator extends PriceDecorator
{

    public function __construct(Price $price)
    {
        parent::__construct($price);
    }

    public function calculate(): float
    {
        $this->baseValue = parent::calculate() * 1.2;
        return $this->baseValue;
    }

}