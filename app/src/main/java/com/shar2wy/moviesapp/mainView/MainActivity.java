package com.shar2wy.moviesapp.mainView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.models.Movie;
import com.shar2wy.moviesapp.movieDetailedView.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.MoviesPresenterListener, MoviesAdapter.OnMovieClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    MainPresenter mainPresenter;

    RecyclerView recyclerViewMovies;
    MoviesAdapter moviesAdapter;
    List<Movie> mMovies = new ArrayList<>();
    GridLayoutManager layoutManager;

    boolean isLoading = false, isLastPage = false;
    public static final int PAGE_SIZE = 20;
    private int currentPage = 1;
    private int totalPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainPresenter = new MainPresenter(this, this);
        mainPresenter.getMovies();

        recyclerViewMovies = (RecyclerView) findViewById(R.id.recycler_view_movies);
        recyclerViewMovies.setHasFixedSize(true);
        recyclerViewMovies.setItemAnimator(new DefaultItemAnimator());

        moviesAdapter = new MoviesAdapter(this, mMovies);
        moviesAdapter.setOnMovieClickListener(this);
        layoutManager = new GridLayoutManager(this, 2);

        recyclerViewMovies.setLayoutManager(layoutManager);
        recyclerViewMovies.setAdapter(moviesAdapter);
//        recyclerViewMovies.setItemAnimator(new SlideInUpAnimator());
        recyclerViewMovies.addOnScrollListener(recyclerViewOnScrollListener);


    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage && totalPages > currentPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        mainPresenter.getMovies(currentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onGetMoviesSuccess(List<Movie> movies, int totalPages) {
        this.totalPages = totalPages;
        isLoading = false;
        mMovies.addAll(movies);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMoviesFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMovieClick(@NonNull Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class)
                .putExtra(DetailsActivity.DETAILED_MOVIE, movie);
        startActivity(intent);
    }
}
