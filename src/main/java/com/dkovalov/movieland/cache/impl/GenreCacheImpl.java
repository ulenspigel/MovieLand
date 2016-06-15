package com.dkovalov.movieland.cache.impl;

import com.dkovalov.movieland.cache.GenreCache;
import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.dto.MovieGenre;
import com.dkovalov.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenreCacheImpl implements GenreCache {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private LocalDateTime lastRefreshed = null;
    private List<MovieGenre> movieGenres;

    @Autowired
    private GenreService genreService;

    @Value("${cache.refresh.interval.hours}")
    private int cacheRefreshInterval;

    @Override
    public List<Genre> getForMovie(int movieId) {
        log.debug("Start fetching genres for movie with ID = {} using cache", movieId);
        long startTime = System.currentTimeMillis();
        refreshIfNecessary();
        ArrayList<Genre> genres = new ArrayList<>();
        for (MovieGenre movieGenre : movieGenres) {
            if (movieGenre.getMovieId() == movieId) {
                genres.add(movieGenre.getGenre());
            } else if (genres.size() > 0) {
                break;
            }
        }
        log.debug("Finish fetching genres from cache. Elapsed time = {} ms", System.currentTimeMillis() - startTime);
        return genres;
    }

    private synchronized void refreshIfNecessary() {
        if (lastRefreshed == null || LocalDateTime.now().isAfter(lastRefreshed.plusHours(cacheRefreshInterval))) {
            log.debug("Refreshing genres cache as it has expired or was not loaded yet");
            movieGenres = genreService.getForAllMovies();
            lastRefreshed = LocalDateTime.now();
        }
    }
}
