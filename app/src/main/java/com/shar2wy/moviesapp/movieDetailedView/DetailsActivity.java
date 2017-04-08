package com.shar2wy.moviesapp.movieDetailedView;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.models.Movie;
import com.shar2wy.moviesapp.models.Review;
import com.shar2wy.moviesapp.models.Trailer;
import com.shar2wy.moviesapp.movieDetailedView.adapters.ReviewAdapter;
import com.shar2wy.moviesapp.movieDetailedView.adapters.TrailerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements
        DetailedViewPresenter.ReviewsPresenterListener,
        DetailedViewPresenter.TrailersPresenterListener, TrailerAdapter.OnTrailerClickListener{

    public static final String DETAILED_MOVIE = "detailed_movie";

    private Movie mMovie;
    private ImageView mImageView;

    private TextView mTitleView;
    private TextView mOverviewView;
    private TextView mDateView;
    private TextView mVoteAverageView;

    private RecyclerView mTrailersView;
    private RecyclerView mReviewsView;

    private CardView mReviewsCardView;
    private CardView mTrailersCardView;

    private TrailerAdapter mTrailerAdapter;
    private RecyclerView.Adapter mReviewAdapter;

    private List<Trailer> mTrailers = new ArrayList<>();
    private List<Review> mReviews = new ArrayList<>();

    private DetailedViewPresenter mDetailedViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    private void initViews() {

        mImageView = (ImageView) findViewById(R.id.detailed_image);

        mTitleView = (TextView) findViewById(R.id.detailed_title);
        mOverviewView = (TextView) findViewById(R.id.detailed_overview);
        mDateView = (TextView) findViewById(R.id.detailed_date);
        mVoteAverageView = (TextView) findViewById(R.id.detailed_vote_average);

        mTrailersView = (RecyclerView) findViewById(R.id.detailed_trailers);
        mReviewsView = (RecyclerView) findViewById(R.id.detailed_reviews);

        mReviewsCardView = (CardView) findViewById(R.id.detail_reviews_cardview);
        mTrailersCardView = (CardView) findViewById(R.id.detail_trailers_cardview);

        mTrailersView.setHasFixedSize(true);
        mTrailersView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mReviewsView.setHasFixedSize(true);
        mReviewsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mTrailerAdapter = new TrailerAdapter(this, mTrailers);
        mTrailerAdapter.setOnTrailerClickListener(this);

        mTrailersView.setAdapter(mTrailerAdapter);

        mReviewAdapter = new ReviewAdapter(this, mReviews);
        mReviewsView.setAdapter(mReviewAdapter);

        if (getIntent() != null && getIntent().getExtras() != null) {
            mMovie = (Movie) getIntent().getExtras().getSerializable(DETAILED_MOVIE);
        }

        if (mMovie != null) {
            mDetailedViewPresenter = new DetailedViewPresenter(mMovie.getId(),this,this,this);
            mDetailedViewPresenter.getReviews();
            mDetailedViewPresenter.getTrailers();

            Glide
                    .with(this)
                    .load(buildImageUrl(342, mMovie.getBackdropPath()))
                    .crossFade()
                    .into(mImageView);

            mTitleView.setText(mMovie.getTitle());
            mOverviewView.setText(mMovie.getOverview());

            mDateView.setText(mMovie.getReleaseDate());

            mVoteAverageView.setText(mMovie.getVoteAverage() + "/10");
        }
    }

    private void openTrailer(Trailer trailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
        startActivity(intent);
    }

    public static String buildImageUrl(int width, String fileName) {
        return "http://image.tmdb.org/t/p/w" + Integer.toString(width) + fileName;
    }

    @Override
    public void onGetReviewsSuccess(List<Review> reviews) {
        mReviewsCardView.setVisibility(View.VISIBLE);
        mReviews.addAll(reviews);
        mReviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetReviewsFail(String error) {
        showMessage(error);
    }

    @Override
    public void onGetTrailersSuccess(List<Trailer> trailers) {
        mTrailersCardView.setVisibility(View.VISIBLE);
        mTrailers.addAll(trailers);
        mTrailerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetTrailersFail(String error) {
        showMessage(error);
    }

    private void showMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnTrailerClick(@NonNull Trailer trailer) {
        openTrailer(trailer);
    }
}
