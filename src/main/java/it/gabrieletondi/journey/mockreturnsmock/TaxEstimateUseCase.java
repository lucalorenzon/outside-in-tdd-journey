package it.gabrieletondi.journey.mockreturnsmock;

public class TaxEstimateUseCase {
    private final TaxService taxService;

    public TaxEstimateUseCase(TaxService taxService) {
        this.taxService = taxService;
    }

    public Money estimateFor(Product product) {
        TaxStrategy strategy = taxService.taxStrategyFor(product);
        Tax tax = strategy.taxFor(product);

        return tax.applyOn(product.getPrice());
    }
}
