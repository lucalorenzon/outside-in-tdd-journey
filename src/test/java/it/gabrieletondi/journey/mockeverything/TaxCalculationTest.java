package it.gabrieletondi.journey.mockeverything;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaxCalculationTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final ProductRepository productRepository = context.mock(ProductRepository.class);
    private final Product product = context.mock(Product.class);
    private final Tax tax = context.mock(Tax.class);

    private final TaxCalculation taxCalculation = new TaxCalculation(productRepository);

    @Test
    public void taxForFoodProduct() {
        context.checking(new Expectations(){{
            allowing(productRepository).findById("productId");
            will(returnValue(product));

            allowing(product).getTax();
            will(returnValue(tax));

            allowing(product).getPrice();
            will(returnValue(10000));

            allowing(tax).getPercentage();
            will(returnValue(10));
        }});

        assertEquals(1000, taxCalculation.taxFor("productId"));
    }
}