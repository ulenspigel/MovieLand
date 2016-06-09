package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.CountryDao;
import com.dkovalov.movieland.dao.mapper.CountryRowMapper;
import com.dkovalov.movieland.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("countryDao")
public class CountryDaoImpl implements CountryDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${sql.country.forMovie}")
    private String fetchForMovieSQL;

    @Override
    public List<Country> getForMovie(int movieId) {
        log.info("Start querying countries for movie with ID = {}", movieId);
        long startTime = System.currentTimeMillis();
        List<Country> countries = jdbcTemplate.query(fetchForMovieSQL, new Object[]{movieId}, new CountryRowMapper());
        log.info("Finish querying countries for movie with ID = {}. Elapsed time - {} ms", movieId,
                System.currentTimeMillis() - startTime);
        return countries;
    }
}
