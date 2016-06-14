package com.dkovalov.movieland.entity;

public class MovieRequest {
    private String genre;
    private String title;
    private Integer year;
    private String country;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "MovieSearch{" +
                "genre='" + genre + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", country='" + country + '\'' +
                '}';
    }
}
