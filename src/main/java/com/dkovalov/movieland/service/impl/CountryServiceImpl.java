package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.dao.CountryDao;
import com.dkovalov.movieland.entity.Country;
import com.dkovalov.movieland.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getForMovie(int movieId) {
        return countryDao.getForMovie(movieId);
    }
}
