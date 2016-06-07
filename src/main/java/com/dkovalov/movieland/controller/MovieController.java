package com.dkovalov.movieland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/")
public class MovieController {
    //TODO: logging framework

    @RequestMapping(name = "movies", method = RequestMethod.GET)
    @ResponseBody
    public String getAllMovies() {
        String moviesJson = "dummy";
        return moviesJson;
    }
}
