package it.gabrieletondi.journey.mockreturnsmock;

public interface TaxService {
    TaxStrategy taxStrategyFor(Product product);
}
