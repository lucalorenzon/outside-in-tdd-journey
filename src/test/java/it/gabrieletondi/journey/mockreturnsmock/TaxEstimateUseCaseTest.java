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
    private final TaxStrategy taxStrategy = context.mock(TaxStrategy.class);
    private final Tax tax = context.mock(Tax.class);

    private final TaxEstimateUseCase taxEstimateUseCase = new TaxEstimateUseCase(taxService);

    private static final Money PRODUCT_PRICE = Money.euro(120);
    private static final FoodProduct PRODUCT = new FoodProduct(PRODUCT_PRICE);

    @Test
    public void estimatesTaxes() {
        context.checking(new Expectations(){{
            allowing(taxService).taxStrategyFor(PRODUCT);
            will(returnValue(taxStrategy));

            allowing(taxStrategy).taxFor(PRODUCT);
            will(returnValue(tax));

            allowing(tax).applyOn(PRODUCT_PRICE);
            will(returnValue(Money.euro(20)));
        }});

        Money estimatedTax = taxEstimateUseCase.estimateFor(PRODUCT);

        assertEquals(Money.euro(20), estimatedTax);
    }
}