package com.dkovalov.movieland.controller;

import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.service.MovieService;
import com.dkovalov.movieland.util.JsonDisplayScheme;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/v1/")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    MovieService movieService;

    @JsonView(JsonDisplayScheme.MovieConcise.class)
    @RequestMapping(value = "movies", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Movie> getAll() {
        log.info("Received request for getting all movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieService.getAll();
        movieService.populateGenres(movies);
        log.info("List of {} elements containing all movies is fetched. Elapsed time - {} ms", movies.size(),
                System.currentTimeMillis() - startTime);
        return movies;
    }

    @JsonView(JsonDisplayScheme.MovieFull.class)
    @RequestMapping(value = "movie/{movieId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Movie getById(@PathVariable int movieId) {
        log.info("Received request for getting a movie by ID = {}", movieId);
        long startTime = System.currentTimeMillis();
        Movie movie = movieService.getById(movieId);
        movieService.populateGenres(movie);
        movieService.populateCountries(movie);
        movieService.populateReviews(movie);
        log.info("Movie with ID = {} is fetched. Elapsed time - {} ms", movieId, System.currentTimeMillis() - startTime);
        return movie;
    }
}
