package it.gabrieletondi.journey.contractviolation;

public class InMemoryProductCatalogTest extends ProductCatalogContractTest {
    @Override
    protected ProductCatalog aProductCatalogWith(Product... products) {
        return new InMemoryProductCatalog(products);
    }
}