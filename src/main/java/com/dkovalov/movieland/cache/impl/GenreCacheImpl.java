package com.dkovalov.movieland.cache.impl;

import com.dkovalov.movieland.cache.GenreCache;
import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.entity.MovieGenre;
import com.dkovalov.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenreCacheImpl implements GenreCache {
    private LocalDateTime lastRefreshed = null;
    private List<MovieGenre> movieGenres;

    @Autowired
    private GenreService genreService;

    @Value("${cache.refresh.interval.hours}")
    private int cacheRefreshInterval;

    @Override
    public List<Genre> getForMovie(int movieId) {
        refreshIfNecessary();
        ArrayList<Genre> genres = new ArrayList<>();
        for (MovieGenre movieGenre : movieGenres) {
            if (movieGenre.getMovieId() == movieId) {
                genres.add(movieGenre.getGenre());
            }
            // TODO: exit on movieID changed from a required value to another one since the list is ordered by movie_id
        }
        return genres;
    }

    // TODO: thread safe
    private synchronized void refreshIfNecessary() {
        if (lastRefreshed == null || LocalDateTime.now().isAfter(lastRefreshed.plusHours(cacheRefreshInterval))) {
            movieGenres = genreService.getForAllMovies();
            lastRefreshed = LocalDateTime.now();
        }
    }
}
