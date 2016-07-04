package com.dkovalov.movieland.dto;

import com.dkovalov.movieland.entity.Genre;

public class MovieGenre {
    private int movieId;
    private Genre genre;

    public MovieGenre(int movieId, Genre genre) {
        this.movieId = movieId;
        this.genre = genre;
    }

    public MovieGenre() {
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "MovieGenre{" +
                "movieId=" + movieId +
                ", genre=" + genre +
                '}';
    }
}
