package it.gabrieletondi.journey.contractviolation;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class PointOfSaleTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final ProductCatalog catalog = context.mock(ProductCatalog.class);
    private final Display display = context.mock(Display.class);
    private final PointOfSale pointOfSale = new PointOfSale(catalog, display);

    @Test
    public void productNotFound() {
        context.checking(new Expectations(){{
            allowing(catalog).findByBarcode("123456");
            will(throwException(new ProductNotFoundException()));

            oneOf(display).showProductNotFound();
        }});

        pointOfSale.onScannedBarcode("123456");
    }

    @Test
    public void productFound() {
        context.checking(new Expectations(){{
            allowing(catalog).findByBarcode("123456");
            will(returnValue(new Product("123456")));

            oneOf(display).showProduct(new Product("123456"));
        }});

        pointOfSale.onScannedBarcode("123456");
    }
}