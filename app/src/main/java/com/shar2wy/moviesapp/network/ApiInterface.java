package com.shar2wy.moviesapp.network;

import com.shar2wy.moviesapp.models.MoviesResponse;
import com.shar2wy.moviesapp.models.ReviewsResponse;
import com.shar2wy.moviesapp.models.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shar2wy on 4/7/17.
 */

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey,@Query("page")int page);


    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewsResponse> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailersResponse> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);

}
