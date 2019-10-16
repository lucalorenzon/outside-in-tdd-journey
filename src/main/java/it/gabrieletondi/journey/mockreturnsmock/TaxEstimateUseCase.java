package it.gabrieletondi.journey.mockreturnsmock;

public class TaxEstimateUseCase {
    private final TaxService taxService;
    private final ProductRepository productRepository;

    public TaxEstimateUseCase(TaxService taxService, ProductRepository productRepository) {
        this.taxService = taxService;
        this.productRepository = productRepository;
    }

    public Money estimateForProductWithId(int productId) {
        Product product = productRepository.findById(productId);
        return taxService.calculateTaxFor(product);
    }
}
