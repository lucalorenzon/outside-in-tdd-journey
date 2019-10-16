package it.gabrieletondi.journey.mocklibraries;

import java.util.HashMap;

public class ProductRepository {
    private final JDBCTemplate jdbcTemplate;

    public ProductRepository(JDBCTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findById(int id) {
        HashMap<String, Object> row = jdbcTemplate.execute("SELECT * FROM products where id = ?", id);
        return new Product((Integer) row.get("id"));
    }
}
