package com.dkovalov.movieland.cache.impl;

import com.dkovalov.movieland.cache.GenreCache;
import com.dkovalov.movieland.dto.MovieGenre;
import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreCacheImpl implements GenreCache {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private List<MovieGenre> movieGenres = new ArrayList<>();

    @Autowired
    private GenreService genreService;

    @Override
    public List<Genre> getForMovie(int movieId) {
        log.debug("Start fetching genres for movie with ID = {} using cache", movieId);
        long startTime = System.currentTimeMillis();
        ArrayList<Genre> genres = new ArrayList<>();
        for (MovieGenre movieGenre : movieGenres) {
            if (movieGenre.getMovieId() == movieId) {
                genres.add(new Genre(movieGenre.getGenre().getId(), movieGenre.getGenre().getName()));
            } else if (genres.size() > 0) {
                // list is stored ordered by movie ID, thus it's not necessary to proceed with list scanning
                // when loop has passed the needed movie ID
                break;
            }
        }
        log.debug("Finish fetching genres from cache. Elapsed time = {} ms", System.currentTimeMillis() - startTime);
        return genres;
    }

    @Scheduled(cron = "0 0 0/4 * * *")
    public void refresh() {
        log.info("Refreshing genres cache. Current cache size is {}", movieGenres.size());
        long startTime = System.currentTimeMillis();
        List<MovieGenre> actualData = genreService.getForAllMovies();
        log.debug("Actual data is fetched in {} ms", System.currentTimeMillis() - startTime);
        synchronized (this) {
            movieGenres.clear();
            movieGenres.addAll(actualData);
        }
        log.info("New cache size is {}", movieGenres.size());
    }

    @PostConstruct
    public void init() {
        refresh();
    }

    @Override
    public void refreshForMovie(int movieId) {
        log.info("Refreshing genres cache for movie with ID = {}", movieId);
        long startTime = System.currentTimeMillis();
        List<MovieGenre> actualMovieGenres = genreService.getForMovie(movieId).stream().
                map(genre -> new MovieGenre(movieId, genre)).collect(Collectors.toList());
        log.debug("Actual data is fetched in {} ms", System.currentTimeMillis() - startTime);
        synchronized (this) {
            boolean isMovieEntryFound = false;
            for (int i = 0; i < movieGenres.size(); i++) {
                if (movieGenres.get(i).getMovieId() == movieId) {
                    isMovieEntryFound = true;
                    movieGenres.remove(i);
                } else if (isMovieEntryFound) {
                    // list is stored ordered by movie ID, thus it's not necessary to proceed with list scanning
                    // when loop has passed the needed movie ID
                    break;
                }
            }
            movieGenres.addAll(actualMovieGenres);
        }
        log.info("Refreshing genres cache for movie has completed");
    }
}
