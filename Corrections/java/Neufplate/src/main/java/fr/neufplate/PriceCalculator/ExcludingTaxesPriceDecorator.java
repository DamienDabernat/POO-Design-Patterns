package fr.neufplate.PriceCalculator;

public class ExcludingTaxesPriceDecorator extends PriceDecorator {
    public ExcludingTaxesPriceDecorator(Price price) {
        super(price);
    }

    @Override
    public double calculate() {
        this.baseValue = super.calculate() * 1000.0d;
        return this.baseValue;
    }
}
