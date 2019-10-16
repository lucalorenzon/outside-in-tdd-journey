package it.gabrieletondi.journey.mocklibraries;

import java.util.HashMap;

// let's suppose this is a jdbc template from an external library/framework
public interface JDBCTemplate {
    HashMap<String, Object> execute(String sql, Object... parameters);
}
