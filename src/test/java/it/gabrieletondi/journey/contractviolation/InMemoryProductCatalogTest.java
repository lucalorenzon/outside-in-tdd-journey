package it.gabrieletondi.journey.contractviolation;

import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryProductCatalogTest {
    private final InMemoryProductCatalog catalog = new InMemoryProductCatalog(
            new Product("123456"),
            new Product("987654")
    );

    @Test
    public void notExistingProduct() {
        assertNull(catalog.findByBarcode("this does not exists"));
    }

    @Test
    public void existingProduct() {
        assertEquals(new Product("123456"), catalog.findByBarcode("123456"));
    }
}