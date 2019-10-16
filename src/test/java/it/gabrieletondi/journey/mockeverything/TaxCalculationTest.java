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

    private final TaxCalculation taxCalculation = new TaxCalculation(productRepository);

    @Test
    public void taxForFoodProduct() {
        context.checking(new Expectations(){{
            allowing(productRepository).findById("productId");
            will(returnValue(new Product(new Tax(10), 10000)));
        }});

        assertEquals(1000, taxCalculation.taxFor("productId"));
    }
}