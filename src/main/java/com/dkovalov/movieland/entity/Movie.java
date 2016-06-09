package com.dkovalov.movieland.entity;

import com.fasterxml.jackson.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import static com.dkovalov.movieland.util.JsonDisplayScheme.*;

@JsonPropertyOrder({"title","originalTitle","yearOfRelease","countries","genres","description","reviews","rating"})
public class Movie {
    @JsonIgnore
    private int id;
    @JsonProperty("title")
    @JsonView(MovieConcise.class)
    private String title;
    @JsonProperty("originalTitle")
    @JsonView(MovieConcise.class)
    private String originalTitle;
    @JsonProperty("yearOfRelease")
    @JsonView(MovieConcise.class)
    private int year;
    @JsonProperty("description")
    @JsonView(MovieFull.class)
    private String description;
    @JsonProperty("rating")
    @JsonView(MovieConcise.class)
    private BigDecimal rating;
    @JsonIgnore
    private BigDecimal price;
    @JsonProperty("genres")
    @JsonView(MovieConcise.class)
    private List<Genre> genres;
    @JsonProperty("countries")
    @JsonView(MovieFull.class)
    private List<Country> countries;
    @JsonProperty("reviews")
    @JsonView(MovieFull.class)
    private List<Review> reviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
