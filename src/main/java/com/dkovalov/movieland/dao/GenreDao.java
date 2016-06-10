package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Genre;
import java.util.List;

public interface GenreDao {
    List<Genre> getForMovie(int movieId);
}
