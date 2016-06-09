package com.dkovalov.movieland.entity;

import com.dkovalov.movieland.util.JsonDisplayScheme;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class Country {
    @JsonIgnore
    int id;
    @JsonProperty("country")
    @JsonView(JsonDisplayScheme.MovieFull.class)
    String name;

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
}
