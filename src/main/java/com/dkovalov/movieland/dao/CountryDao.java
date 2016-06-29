package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Country;
import java.util.List;

public interface CountryDao {
    List<Country> getForMovie(int moveiId);
    int getIdByName(String name);
    int add(String name);
    void addForMovie(int movieId, int countryId);
    void deleteForMovie(int movieId);
}
