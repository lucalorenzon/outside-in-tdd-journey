package it.gabrieletondi.journey.contractviolation;

import java.util.Arrays;
import java.util.List;

public class InMemoryProductCatalog implements ProductCatalog {
    private final List<Product> products;

    public InMemoryProductCatalog(Product... products) {
        this.products = Arrays.asList(products);
    }

    @Override
    public Product findByBarcode(String barcode) {
        return products.stream()
                .filter(product -> product.hasBarcode(barcode))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }
}
