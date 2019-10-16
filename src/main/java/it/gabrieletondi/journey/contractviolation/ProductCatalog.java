package it.gabrieletondi.journey.contractviolation;

public interface ProductCatalog {
    Product findByBarcode(String barcode);
}
