package com.dkovalov.movieland.service;

import com.dkovalov.movieland.entity.Genre;
import java.util.List;

public interface GenreService {
    List<Genre> getForMovie(int movieId);
}
