package com.dkovalov.movieland.entity;

import com.dkovalov.movieland.util.JsonDisplayScheme;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class Genre {
    @JsonIgnore
    private int id;

    @JsonProperty("genre")
    @JsonView(JsonDisplayScheme.MovieConcise.class)
    private String name;

    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
