package com.dkovalov.movieland.controller;

import com.dkovalov.movieland.entity.Movie;
import com.dkovalov.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MovieController {
    //TODO: logging framework
    @Autowired
    MovieService movieService;

    @RequestMapping(value = "/v1/movies", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Movie> getAllMovies() {
        return movieService.getAll();
    }
}
