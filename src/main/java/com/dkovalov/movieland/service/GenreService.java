package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dao.GenreDao;
import com.dkovalov.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedReader;
import java.io.IOException;

public class GenreService implements DataLoader {
    //TODO: move to XML
    @Autowired
    GenreDao genreDao;

    @Override
    public void loadFromFile(BufferedReader reader) throws IOException {
        String entry = "";
        while ((entry = reader.readLine()) != null) {
            Genre genre = new Genre(entry);
            genreDao.addGenre(genre);
        }
    }
}
