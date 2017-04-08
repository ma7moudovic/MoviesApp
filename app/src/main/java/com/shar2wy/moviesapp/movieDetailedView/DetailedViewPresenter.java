package com.shar2wy.moviesapp.movieDetailedView;

import android.content.Context;

import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.models.Review;
import com.shar2wy.moviesapp.models.ReviewsResponse;
import com.shar2wy.moviesapp.models.Trailer;
import com.shar2wy.moviesapp.models.TrailersResponse;
import com.shar2wy.moviesapp.network.ApiClient;
import com.shar2wy.moviesapp.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shar2wy on 4/8/17.
 */

public class DetailedViewPresenter {

    private final Context context;
    private ReviewsPresenterListener mReviewsListener;
    private TrailersPresenterListener mTrailersListener;
    private int movieId;

    public interface TrailersPresenterListener {
        void onGetTrailersSuccess(List<Trailer> trailers);

        void onGetTrailersFail(String error);
    }

    public interface ReviewsPresenterListener {
        void onGetReviewsSuccess(List<Review> reviews);

        void onGetReviewsFail(String error);
    }


    public DetailedViewPresenter(int id, ReviewsPresenterListener reviewsListener, TrailersPresenterListener trailersListener, Context context) {
        this.mReviewsListener = reviewsListener;
        this.mTrailersListener = trailersListener;
        this.context = context;
        this.movieId = id;
    }

    public void getReviews(){
        ApiClient
                .getClient()
                .create(ApiInterface.class)
                .getMovieReviews(movieId,context.getString(R.string.api_key))
                .enqueue(new Callback<ReviewsResponse>() {
                    @Override
                    public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                        ReviewsResponse result = response.body();

                        if(result != null)
                            mReviewsListener.onGetReviewsSuccess(result.getResults());
                    }

                    @Override
                    public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                        mReviewsListener.onGetReviewsFail(t.getMessage());
                    }
                });
    }

    public void getTrailers(){
        ApiClient
                .getClient()
                .create(ApiInterface.class)
                .getMovieTrailers(movieId,context.getString(R.string.api_key))
                .enqueue(new Callback<TrailersResponse>() {
                    @Override
                    public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                        TrailersResponse result = response.body();

                        if(result != null)
                            mTrailersListener.onGetTrailersSuccess(result.getResults());
                    }

                    @Override
                    public void onFailure(Call<TrailersResponse> call, Throwable t) {
                        mTrailersListener.onGetTrailersFail(t.getMessage());
                    }
                });
    }

}
