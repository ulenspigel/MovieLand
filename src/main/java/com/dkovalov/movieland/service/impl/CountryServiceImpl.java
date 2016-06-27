package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.dao.CountryDao;
import com.dkovalov.movieland.entity.Country;
import com.dkovalov.movieland.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getForMovie(int movieId) {
        return countryDao.getForMovie(movieId);
    }

    @Override
    public void addForMovie(int movieId, List<Country> countries) {
        for (Country country : countries) {
            int countryId;
            try {
                countryId = countryDao.getIdByName(country.getName());
            } catch (EmptyResultDataAccessException e) {
                log.info("Country with name {} was not found in DB. Inserting record", country.getName());
                countryId = countryDao.add(country.getName());
            }
            countryDao.addForMovie(movieId, countryId);
        }
    }
}
