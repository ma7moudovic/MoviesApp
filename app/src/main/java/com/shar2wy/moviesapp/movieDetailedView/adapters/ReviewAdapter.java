package com.shar2wy.moviesapp.movieDetailedView.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.models.Review;

import java.util.List;

/**
 * Created by shar2wy on 4/8/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private Context mContext;
    private List<Review> mReviews;

    public ReviewAdapter(Context mContext, List<Review> mReviews) {
        this.mContext = mContext;
        this.mReviews = mReviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);

        holder.reviewAuthor.setText(review.getAuthor());
        holder.reviewContent.setText(Html.fromHtml(review.getContent()));

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{

        public final TextView reviewAuthor;
        public final TextView reviewContent;

        public ReviewViewHolder(View view) {
            super(view);
            reviewAuthor = (TextView) view.findViewById(R.id.review_author);
            reviewContent= (TextView) view.findViewById(R.id.review_content);
        }
    }
}
