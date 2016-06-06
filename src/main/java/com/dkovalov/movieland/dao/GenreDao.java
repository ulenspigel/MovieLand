package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.Genre;

public interface GenreDao {
    void addGenre(Genre genre);
    Genre getByName(String name);
}
