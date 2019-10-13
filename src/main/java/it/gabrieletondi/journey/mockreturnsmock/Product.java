package it.gabrieletondi.journey.mockreturnsmock;

public abstract class Product {
    private final Money price;

    protected Product(Money price) {
        this.price = price;
    }

    public Money getPrice() {
        return price;
    }
}
