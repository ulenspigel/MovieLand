package com.dkovalov.movieland.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashSet;
import java.util.Set;

public class QueryBuilder {
    private static final Logger log = LoggerFactory.getLogger(QueryBuilder.class);
    private static final String ORDER_BY_CLAUSE = " order by ";
    private static final String RATING_COLUMN_NAME = ",rating ";
    private static final String PRICE_COLUMN_NAME = ",price ";
    private static final Set<String> validOrders = new HashSet<String>() {{
        add("asc");
        add("desc");
    }};

    public static String getMoviesOrderClause(String ratingOrder, String priceOrder) {
        String orderClause = "";
        if (ratingOrder != null) {
            validateSortOrder(ratingOrder);
            orderClause += RATING_COLUMN_NAME + ratingOrder;
        }
        if (priceOrder != null) {
            validateSortOrder(priceOrder);
            orderClause += PRICE_COLUMN_NAME + priceOrder;
        }
        if (!"".equals(orderClause)) {
            orderClause = ORDER_BY_CLAUSE + orderClause.substring(1);
        }
        return orderClause;
    }

    private static void validateSortOrder(String order) {
        if (!validOrders.contains(order)) {
            log.error("Unsupported sort operation {}", order);
            throw new IllegalArgumentException("Unsupported sort operation: " + order);
        }
    }
}
