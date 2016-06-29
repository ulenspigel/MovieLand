package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.CountryDao;
import com.dkovalov.movieland.dao.mapper.CountryRowMapper;
import com.dkovalov.movieland.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CountryDaoImpl implements CountryDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static RowMapper<Country> rowMapper = new CountryRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${sql.country.forMovie}")
    private String fetchForMovieSQL;

    @Value("${sql.country.byName}")
    private String fetchByNameSQL;

    @Value("${sql.country.add}")
    private String addCountrySQL;

    @Value("${sql.movie.addCountry}")
    private String addMovieCountrySQL;

    @Value("${sql.movie.deleteCountries}")
    private String deleteMovieCountriesSQL;

    @Override
    public List<Country> getForMovie(int movieId) {
        log.info("Start querying countries for movie with ID = {}", movieId);
        long startTime = System.currentTimeMillis();
        List<Country> countries = jdbcTemplate.query(fetchForMovieSQL, new Object[]{movieId}, rowMapper);
        log.info("Finish querying countries for movie with ID = {}. Elapsed time - {} ms", movieId,
                System.currentTimeMillis() - startTime);
        return countries;
    }

    @Override
    public int getIdByName(String name) {
        log.info("Start searching country by its name {}", name);
        long startTime = System.currentTimeMillis();
        Country country = jdbcTemplate.queryForObject(fetchByNameSQL, new Object[] {name}, rowMapper);
        log.debug("Fetched country is {}", country);
        log.info("Finish searching country. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return country.getId();
    }

    @Override
    public int add(String name) {
        log.info("Start adding country with name {}", name);
        long startTime = System.currentTimeMillis();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(addCountrySQL, new String[] {"country_id"});
                statement.setString(1, name);
                return statement;
            }
        }, keyHolder);
        log.info("Finish adding country. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void addForMovie(int movieId, int countryId) {
        log.info("Start adding country with ID={} for movie with ID={}", countryId, movieId);
        long startTime = System.currentTimeMillis();
        jdbcTemplate.update(addMovieCountrySQL, new Object[] {movieId, countryId});
        log.info("Row has been inserted. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
    }

    @Override
    public void deleteForMovie(int movieId) {
        log.info("Start deleting countries for a movie with ID={}", movieId);
        long startTime = System.currentTimeMillis();
        int rowsDeleted = jdbcTemplate.update(deleteMovieCountriesSQL, new Object[] {movieId});
        log.info("{} rows have been deleted. Elapsed time - {} ms", rowsDeleted, System.currentTimeMillis() - startTime);
    }
}
