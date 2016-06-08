package com.dkovalov.movieland.controller;

import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    MovieService movieService;

    @RequestMapping(value = "/v1/movies", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Movie> getAllMovies() {
        log.info("Received request for getting all movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieService.getAll();
        log.info("List of {} elements containing all movies is received. Time elapsed - {} ms", movies.size(),
                System.currentTimeMillis() - startTime);
        return movies;
    }
}
