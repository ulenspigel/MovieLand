package com.dkovalov.movieland.dao.util;

import com.dkovalov.movieland.entity.MovieRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QueryBuilder {
    private final Logger log = LoggerFactory.getLogger(QueryBuilder.class);
    private static final String ORDER_BY_CLAUSE = " order by ";
    private static final String WHERE_CLAUSE = "where";
    private static final String RATING_COLUMN_NAME = ",rating ";
    private static final String PRICE_COLUMN_NAME = ",price ";
    private static final String TITLE_PREDICATE = " and title = ?";
    private static final String YEAR_PREDICATE = " and year = ?";
    private final Set<String> validOrders = new HashSet<String>() {{
        add("asc");
        add("desc");
    }};

    @Value("${sql.movie.genreSubRequest}")
    private String genreSubRequest;

    @Value("${sql.movie.countrySubRequest}")
    private String countrySubRequest;

    public String getMoviesOrderClause(String ratingOrder, String priceOrder) {
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

    // TODO: cover with test
    public void getMoviesFilterPredicate(MovieRequest request, String whereClause, Object[] params) {
        whereClause = "";
        List<Object> paramList = new ArrayList<>();
        if (request.getTitle() != null) {
            whereClause += TITLE_PREDICATE;
            paramList.add(request.getTitle());
        }
        if (request.getYear() != null) {
            whereClause += YEAR_PREDICATE;
            paramList.add(request.getYear());
        }
        if (request.getGenre() != null) {
            whereClause += " and " + genreSubRequest;
            paramList.add(request.getGenre());
        }
        if (request.getCountry() != null) {
            whereClause += " and " + countrySubRequest;
            paramList.add(request.getCountry());
        }
        if (!"".equals(whereClause)) {
            whereClause.replaceFirst("and", WHERE_CLAUSE);
            params = paramList.toArray();
        }
    }

    private void validateSortOrder(String order) {
        if (!validOrders.contains(order)) {
            log.error("Unsupported sort operation {}", order);
            throw new IllegalArgumentException("Unsupported sort operation: " + order);
        }
    }
}
