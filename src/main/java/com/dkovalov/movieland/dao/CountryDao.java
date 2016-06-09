package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Country;
import java.util.List;

public interface CountryDao {
    List<Country> getForMovie(int moveiId);
}
