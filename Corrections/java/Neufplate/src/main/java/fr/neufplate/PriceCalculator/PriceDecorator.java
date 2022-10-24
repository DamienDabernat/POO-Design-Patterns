package fr.neufplate.PriceCalculator;

public class PriceDecorator extends Price {
    Price price;

    public PriceDecorator(Price price) {
        this.price = price;
    }

    @Override
    public double calculate() {
        return this.price.calculate();
    }

}
