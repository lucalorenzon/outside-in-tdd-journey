package it.gabrieletondi.journey.mocklibraries;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ProductRepositoryTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final JDBCTemplate jdbcTemplate = context.mock(JDBCTemplate.class);
    private final ProductRepository productRepository = new ProductRepository(jdbcTemplate);

    @Test
    public void existingProduct() {
        context.checking(new Expectations(){{
            allowing(jdbcTemplate).execute("SELECT * FROM products where id = ?", 123);
            will(returnValue(new HashMap<String, Object>(){{
                put("id", 123);
            }}));
        }});

        assertEquals(new Product(123), productRepository.findById(123));
    }
}