package com.dkovalov.movieland.service;

import com.dkovalov.movieland.entity.Country;
import java.util.List;

public interface CountryService {
    List<Country> getForMovie(int movieId);
    void addForMovie(int movieId, List<Country> countries);
    void deleteForMovie(int movieId);
}
