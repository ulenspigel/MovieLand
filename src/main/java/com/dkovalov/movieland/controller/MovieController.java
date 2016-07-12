package com.dkovalov.movieland.controller;

import com.dkovalov.movieland.deserializer.impl.RequestDeserializerImpl;
import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.service.MovieService;
import com.dkovalov.movieland.util.JsonDisplayScheme;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @Autowired
    private RequestDeserializerImpl deserializer;

    @JsonView(JsonDisplayScheme.MovieConcise.class)
    @RequestMapping(value = "movies", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Movie> getAll(@RequestParam(required = false) String rating,
                              @RequestParam(required = false) String price) {
        log.info("Received request for getting all movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieService.getAll(rating, price);
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

    @JsonView(JsonDisplayScheme.MovieConcise.class)
    @RequestMapping(value = "movies/search", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Movie> search(@RequestBody String request) {
        log.info("Received request for movies search: {}", request);
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieService.search(deserializer.searchRequest(request));
        movieService.populateGenres(movies);
        log.info("List of {} movies is fetched. Elapsed time - {} ms", movies.size(), System.currentTimeMillis() - startTime);
        return movies;
    }

    @JsonView(JsonDisplayScheme.MovieId.class)
    @RequestMapping(value = "movie", method = RequestMethod.POST)
    @ResponseBody
    public Movie add(@RequestHeader("User-Token") int token, @RequestBody String request) {
        log.info("Received request for adding a movie: {}", request);
        long startTime = System.currentTimeMillis();
        Movie movie = deserializer.editMovieRequest(request);
        movie = movieService.add(token, movie);
        log.info("Movie with ID {} has been added. Elapsed time - {} ms", movie.getId(), System.currentTimeMillis() - startTime);
        return movie;
    }

    @RequestMapping(value = "movie", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestHeader("User-Token") int token, @RequestBody String request) {
        log.info("Received request for editing a movie: {}", request);
        long startTime = System.currentTimeMillis();
        movieService.update(token, deserializer.editMovieRequest(request));
        log.info("Movie has been updated. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
    }

    @RequestMapping(value = "movie/{movieId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void markForDeletion(@RequestHeader("User-Token") int token, @PathVariable int movieId) {
        log.info("Received request for marking a movie with ID {} as pending for deletion", movieId);
        long startTime = System.currentTimeMillis();
        movieService.markForDeletion(token, movieId);
        log.info("Movie has been marked. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
    }

    @RequestMapping(value = "movie/{movieId}/unmark", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void unmarkForDeletion(@RequestHeader("User-Token") int token, @PathVariable int movieId) {
        log.info("Received request for removing a movie with ID {} from the deletion queue", movieId);
        long startTime = System.currentTimeMillis();
        movieService.unmarkForDeletion(token, movieId);
        log.info("Movie has been removed from the deletion queue. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
    }
}