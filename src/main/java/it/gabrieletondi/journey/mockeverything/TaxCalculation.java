package it.gabrieletondi.journey.mockeverything;

public class TaxCalculation {
    private final ProductRepository productRepository;

    public TaxCalculation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int taxFor(String productId) {
        Product product = productRepository.findById(productId);
        int percentage = product.getTax().getPercentage();
        int price = product.getPrice();

        return (price  / 100) * percentage;
    }
}
