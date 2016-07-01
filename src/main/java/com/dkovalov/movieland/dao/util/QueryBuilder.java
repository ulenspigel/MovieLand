package com.dkovalov.movieland.dao.util;

import com.dkovalov.movieland.dto.MovieSearchRequest;
import com.dkovalov.movieland.entity.Movie;
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
    private static final String SET_CLAUSE_PLACEHOLDER = "#set_clause#";
    private static final String TITLE_COLUMN = "movie_title";
    private static final String ORIGINAL_TITLE_COLUMN = "original_title";
    private static final String YEAR_COLUMN = "year";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String RATING_COLUMN = "rating";
    private static final String PRICE_COLUMN = "price";
    private static final String TITLE_PREDICATE = " and " + TITLE_COLUMN + " = ?";
    private static final String YEAR_PREDICATE = " and " + YEAR_COLUMN + " = ?";
    private final Set<String> validOrders = new HashSet<String>() {{
        add("asc");
        add("desc");
    }};

    @Value("${sql.movie.genreSubRequest}")
    private String genreSubRequest;

    @Value("${sql.movie.countrySubRequest}")
    private String countrySubRequest;

    @Value("${sql.movie.update}")
    private String movieUpdateSQL;

    public String getMoviesOrderClause(String ratingOrder, String priceOrder) {
        StringBuilder orderClause = new StringBuilder("");
        if (ratingOrder != null) {
            validateSortOrder(ratingOrder);
            orderClause.append(",").append(RATING_COLUMN).append(" ").append(ratingOrder);
        }
        if (priceOrder != null) {
            validateSortOrder(priceOrder);
            orderClause.append(",").append(PRICE_COLUMN).append(" ").append(priceOrder);
        }
        return !"".equals(orderClause.toString()) ? ORDER_BY_CLAUSE + orderClause.substring(1) : "";
    }

    public String getMoviesFilterPredicate(MovieSearchRequest request) {
        StringBuilder whereClause = new StringBuilder("");
        if (request.getTitle() != null) {
            whereClause.append(TITLE_PREDICATE);
        }
        if (request.getYear() != null) {
            whereClause.append(YEAR_PREDICATE);
        }
        if (request.getGenre() != null) {
            whereClause.append(" and " + genreSubRequest);
        }
        if (request.getCountry() != null) {
            whereClause.append(" and " + countrySubRequest);
        }
        return !"".equals(whereClause.toString()) ? whereClause.toString().replaceFirst("(and)", WHERE_CLAUSE) : "";
    }

    public Object[] getMoviesFilterParams(MovieSearchRequest request) {
        List<Object> params = new ArrayList<>();
        if (request.getTitle() != null) {
            params.add(request.getTitle());
        }
        if (request.getYear() != null) {
            params.add(request.getYear());
        }
        if (request.getGenre() != null) {
            params.add(request.getGenre());
        }
        if (request.getCountry() != null) {
            params.add(request.getCountry());
        }
        return params.toArray();
    }

    //TODO: Tests
    public String getMovieUpdateStatement(Movie movie) {
        StringBuilder statement = new StringBuilder();
        if (movie.getTitle() != null) {
            statement.append(", ").append(TITLE_COLUMN).append(" = '").append(movie.getTitle()).append("'");
        }
        if (movie.getOriginalTitle() != null) {
            statement.append(", ").append(ORIGINAL_TITLE_COLUMN).append(" = '").append(movie.getOriginalTitle()).append("'");
        }
        if (movie.getYear() != 0) {
            statement.append(", ").append(YEAR_COLUMN).append(" = ").append(movie.getYear());
        }
        if (movie.getDescription() != null) {
            statement.append(", ").append(DESCRIPTION_COLUMN).append(" = '").append(movie.getDescription()).append("'");
        }
        if (movie.getRating() != null) {
            statement.append(", ").append(RATING_COLUMN).append(" = ").append(movie.getRating());
        }
        if (movie.getPrice() != null) {
            statement.append(", ").append(PRICE_COLUMN).append(" = ").append(movie.getPrice());
        }
        if (statement.length() == 0) {
            return null;
        } else {
            return movieUpdateSQL.replace(SET_CLAUSE_PLACEHOLDER, statement.toString().substring(1));
        }
    }

    void validateSortOrder(String order) {
        if (!validOrders.contains(order)) {
            log.error("Unsupported sort operation {}", order);
            throw new IllegalArgumentException("Unsupported sort operation: " + order);
        }
    }
}
