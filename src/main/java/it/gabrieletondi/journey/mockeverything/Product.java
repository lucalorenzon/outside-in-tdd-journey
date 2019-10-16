package it.gabrieletondi.journey.mockeverything;

public class Product {
    private final Tax tax;
    private final int price;

    public Product(Tax tax, int price) {
        this.tax = tax;
        this.price = price;
    }

    public Tax getTax() {
        return tax;
    }

    public int getPrice() {
        return price;
    }

}
