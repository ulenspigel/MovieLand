package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.dao.GenreDao;
import com.dkovalov.movieland.entity.Genre;
import com.dkovalov.movieland.dto.MovieGenre;
import com.dkovalov.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final Logger log = LoggerFactory.getLogger(getClass());

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

    @Override
    public void addForMovie(int movieId, List<Genre> genres) {
        for (Genre genre : genres) {
            int genreId;
            try {
                genreId = genreDao.getIdByName(genre.getName());
            } catch(EmptyResultDataAccessException e) {
                log.info("Genre with name {} was not found in DB. Inserting record");
                genreId = genreDao.add(genre.getName());
            }
            genreDao.addForMovie(movieId, genreId);
        }
    }
}
