package it.gabrieletondi.journey.contractviolation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class ProductCatalogContractTest {
    @Test(expected = ProductNotFoundException.class)
    public void notExistingProduct() {
        ProductCatalog catalog = aProductCatalogWith(new Product("this exists"));
        catalog.findByBarcode("this does not exists");
    }

    @Test
    public void existingProduct() {
        ProductCatalog catalog = aProductCatalogWith(
                new Product("123456"),
                new Product("another product"));
        assertEquals(new Product("123456"), catalog.findByBarcode("123456"));
    }

    protected abstract ProductCatalog aProductCatalogWith(Product... products);
}
