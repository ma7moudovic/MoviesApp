package com.shar2wy.moviesapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shar2wy on 4/8/17.
 */

public class TrailersResponse {
    @SerializedName("results")
    private List<Trailer> results;

    public TrailersResponse() {
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
