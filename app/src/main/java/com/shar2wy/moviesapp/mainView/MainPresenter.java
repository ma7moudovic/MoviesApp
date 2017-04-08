package com.shar2wy.moviesapp.mainView;

import android.content.Context;

import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.models.Movie;
import com.shar2wy.moviesapp.models.MoviesResponse;
import com.shar2wy.moviesapp.network.ApiClient;
import com.shar2wy.moviesapp.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shar2wy on 4/8/17.
 */

public class MainPresenter {

    private final Context context;
    private final MoviesPresenterListener mListener;

    public interface MoviesPresenterListener {
        void onGetMoviesSuccess(List<Movie> movies,int totalPages);
        void onGetMoviesFail(String error);
    }

    public MainPresenter(MoviesPresenterListener listener, Context context) {
        this.mListener = listener;
        this.context = context;
    }

    public void getMovies() {
        ApiClient
                .getClient()
                .create(ApiInterface.class)
                .getTopRatedMovies(context.getString(R.string.api_key))
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        MoviesResponse result = response.body();

                        if(result != null)
                            mListener.onGetMoviesSuccess(result.getResults(),result.getTotalPages());
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        mListener.onGetMoviesFail(t.getMessage());
                    }
                });
    }

    public void getMovies(int page) {
        ApiClient
                .getClient()
                .create(ApiInterface.class)
                .getTopRatedMovies(context.getString(R.string.api_key),page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        MoviesResponse result = response.body();

                        if(result != null)
                            mListener.onGetMoviesSuccess(result.getResults(),result.getTotalPages());
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        mListener.onGetMoviesFail(t.getMessage());
                    }
                });
    }
}
