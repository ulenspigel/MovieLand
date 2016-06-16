package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.dao.GenreDao;
import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.dto.MovieGenre;
import com.dkovalov.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreDao genreDao;

    @Override
    public List<Genre> getForMovie(int movieId) {
        return genreDao.getForMovie(movieId);
    }

    @Override
    public List<MovieGenre> getForAllMovies() {
        return genreDao.getForAllMovies();
    }
}
