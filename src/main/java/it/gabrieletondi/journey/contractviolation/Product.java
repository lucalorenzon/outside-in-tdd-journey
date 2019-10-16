package it.gabrieletondi.journey.contractviolation;

import java.util.Objects;

public class Product {
    private final String barCode;

    public Product(String barCode) {
        this.barCode = barCode;
    }

    public boolean hasBarcode(String barCode) {
        return this.barCode.equals(barCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(barCode, product.barCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barCode);
    }

    @Override
    public String toString() {
        return "Product{" +
                "barCode='" + barCode + '\'' +
                '}';
    }
}
