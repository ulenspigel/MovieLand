package com.dkovalov.movieland.entity;

public class MovieGenre {
    int movieId;
    Genre genre;

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
