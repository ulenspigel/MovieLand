package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dao.GenreDao;
import com.dkovalov.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenreService implements DataLoader {
    //TODO: move to XML
    @Autowired
    private GenreDao genreDao;

    @Override
    public void loadFromFile(BufferedReader reader) throws IOException {
        String entry = "";
        while ((entry = reader.readLine()) != null) {
            Genre genre = new Genre(entry);
            genreDao.addGenre(genre);
        }
    }

    public List<Genre> getGenresByNames(String[] names) {
        ArrayList<Genre> genres = new ArrayList<>();
        for (String genreName : names) {
            genres.add(genreDao.getByName(genreName.trim()));
        }
        return genres;
    }
}
