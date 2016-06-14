package com.dkovalov.movieland.cache;

import com.dkovalov.movieland.entity.Genre;
import java.util.List;

public interface GenreCache {
    List<Genre> getForMovie(int movieId);
}
