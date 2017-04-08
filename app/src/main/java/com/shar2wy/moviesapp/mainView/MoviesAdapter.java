package com.shar2wy.moviesapp.mainView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.models.Movie;

import java.util.List;

/**
 * Created by shar2wy on 4/7/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;
    private OnMovieClickListener mOnMovieClickListener;

    public MoviesAdapter(Context mContext, List<Movie> mMovies) {
        this.mMovies = mMovies;
        this.mContext = mContext;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie= mMovies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        String imageUrl = "http://image.tmdb.org/t/p/w185" + movie.getPosterPath();

        Glide
                .with(mContext)
                .load(imageUrl)
                .crossFade()
                .into(holder.moviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnMovieClickListener != null) {
                    mOnMovieClickListener.onMovieClick(movie);
                }
            }
        });
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        mOnMovieClickListener = listener;
    }

    public interface OnMovieClickListener {
        void onMovieClick(@NonNull Movie movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        ImageView moviePoster;


        public MovieViewHolder(View v) {
            super(v);
            movieTitle = (TextView) v.findViewById(R.id.movie_item_title);
            moviePoster = (ImageView) v.findViewById(R.id.movie_item_image);
        }
    }
}
