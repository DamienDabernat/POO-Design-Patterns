package fr.neufplate.PriceCalculator;

public class IncludingTaxesPriceDecorator extends PriceDecorator {
    public IncludingTaxesPriceDecorator(Price price) {
        super(price);
    }

    @Override
    public double calculate() {
        this.baseValue = super.calculate() * 1.2d;
        return this.baseValue;
    }
}
