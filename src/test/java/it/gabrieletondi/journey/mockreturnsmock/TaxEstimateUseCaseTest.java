package it.gabrieletondi.journey.mockreturnsmock;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaxEstimateUseCaseTest {

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final TaxService taxService = context.mock(TaxService.class);
    private final ProductRepository productRepository = context.mock(ProductRepository.class);

    private final TaxEstimateUseCase taxEstimateUseCase = new TaxEstimateUseCase(taxService, productRepository);

    private static final Money PRODUCT_PRICE = Money.euro(120);
    private static final FoodProduct PRODUCT = new FoodProduct(PRODUCT_PRICE);

    @Test
    public void estimatesTaxes() {
        context.checking(new Expectations(){{
            allowing(productRepository).findById(999);
            will(returnValue(PRODUCT));

            allowing(taxService).calculateTaxFor(PRODUCT);
            will(returnValue(Money.euro(20)));
        }});

        Money estimatedTax = taxEstimateUseCase.estimateForProductWithId(999);

        assertEquals(Money.euro(20), estimatedTax);
    }
}