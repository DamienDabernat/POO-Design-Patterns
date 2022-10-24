<?php

namespace App\PriceCalculator;

abstract class Price
{
    public float $baseValue;

    public abstract function calculate(): float;
}