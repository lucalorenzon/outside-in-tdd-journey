package it.gabrieletondi.journey.contractviolation;

public class PointOfSale {
    private final ProductCatalog catalog;
    private final Display display;

    public PointOfSale(ProductCatalog catalog, Display display) {
        this.catalog = catalog;
        this.display = display;
    }

    public void onScannedBarcode(String barcode) {
        try {
            Product product = catalog.findByBarcode(barcode);
            display.showProduct(product);
        } catch (ProductNotFoundException e) {
            display.showProductNotFound();
        }
    }
}
