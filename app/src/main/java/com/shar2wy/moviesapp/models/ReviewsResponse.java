package com.shar2wy.moviesapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shar2wy on 4/8/17.
 */

public class ReviewsResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Review> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public ReviewsResponse() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}