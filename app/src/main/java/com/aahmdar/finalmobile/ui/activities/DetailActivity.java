package com.aahmdar.finalmobile.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aahmdar.finalmobile.ImageSize;
import com.aahmdar.finalmobile.R;
import com.aahmdar.finalmobile.data.api.repository.MovieRepository;
import com.aahmdar.finalmobile.data.api.repository.TvShowRepository;
import com.aahmdar.finalmobile.data.api.repository.callback.OnDetailCallBack;
import com.aahmdar.finalmobile.data.models.Movie;
import com.aahmdar.finalmobile.data.models.TvShow;

import java.util.List;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivBackdrop;
    private ImageView ivPoster;
    private TextView tvName;
    private RatingBar rbRating;
    private TextView tvFirstAirDate;
    private TextView tvLastAirDate;
    private TextView tvSeason;
    private TextView tvEpisode;
    private TextView tvOverview;
    private TextView tvError;
    private TextView tvLabelFirstAirDate;
    private TextView tvLabelLastAirDate;
    private TextView tvLabelEpisode;
    private TextView tvLabelSeason;
    private TvShowRepository tvRepo;
    private MovieRepository movieRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Realm.init(this);

        ivBackdrop = findViewById(R.id.iv_backdrop);
        ivPoster = findViewById(R.id.iv_poster);
        tvName = findViewById(R.id.tv_name);
        rbRating = findViewById(R.id.rb_rating);
        tvFirstAirDate = findViewById(R.id.tv_first_air_date);
        tvLastAirDate = findViewById(R.id.tv_last_air_date);
        tvSeason = findViewById(R.id.tv_season);
        tvEpisode = findViewById(R.id.tv_episode);
        tvOverview = findViewById(R.id.tv_overview);
        tvError = findViewById(R.id.tv_error);
        tvLabelEpisode = findViewById(R.id.label_episode);
        tvLabelSeason = findViewById(R.id.label_season);
        tvLabelFirstAirDate = findViewById(R.id.label_first_air_date);
        tvLabelLastAirDate = findViewById(R.id.label_last_air_date);
        tvRepo = TvShowRepository.getInstance();
        movieRepo = MovieRepository.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_detail_activity, menu);
        // TODO: switch favourite button state
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent mainActivity = new Intent(DetailActivity.this, MainActivity.class);
                mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainActivity.putExtra("SELECTED_FRAGMENT", getIntent().getStringExtra("SELECTED_FRAGMENT"));
                startActivity(mainActivity);
                return true;
            case R.id.item_favorite:
//                if (helper.isFavorite(id)) {
//                    if (helper.delete(id) > 0) {
//                        // TODO: Set favorite button state
//                    }
//                } else {
//                    if (helper.insert(tvShow) > 0) {
//                        // TODO: Set favorite button state
//                    }
//                }
                return true;
            case R.id.item_language_setting:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Integer id = getIntent().getIntExtra("ID", 0);
        Log.d("MEDIA ID", id.toString());
        String selectedFragment = getIntent().getStringExtra("SELECTED_FRAGMENT");
        Log.d("SELECTED FRAGMENT", selectedFragment);

        load(id, selectedFragment);
    }



    private void load(Integer id, String selectedFragment) {
        if (selectedFragment.equals("tv_show")) {
            tvRepo.getTvDetail(id, new OnDetailCallBack<TvShow>() {
                @Override
                public void onSuccess(TvShow media, String message) {
                    String imageUri = media.getPosterPath(ImageSize.W154);
                    String backdropUri = media.getBackdropPath(ImageSize.W200);
                    float rating = (float) (media.getVoteAverage() / 2.0);
                    Log.d("RATING", Float.toString(rating));
                    Glide.with(DetailActivity.this)
                            .load(imageUri)
                            .into(ivPoster);
                    Glide.with(DetailActivity.this)
                            .load(backdropUri)
                            .into(ivBackdrop);
                    tvName.setText(media.getName());
                    tvFirstAirDate.setText(media.getFirstAirDate());
                    tvLastAirDate.setText(media.getLastAirDate());
                    tvEpisode.setText(Integer.toString(media.getNumberOfEpisode()));
                    tvOverview.setText(media.getOverview());
                    tvSeason.setText(Integer.toString(media.getNumberOfSeaon()));
                    rbRating.setRating(rating);
                    setActionBar(media.getName());
                }

                @Override
                public void onFailure(String message) {

                }
            });
        } else {
            movieRepo.getMovieDetail(id, new OnDetailCallBack<Movie>() {
                @Override
                public void onSuccess(Movie media, String message) {
                    Log.d("MOVIE TITLE", media.getTitle());
                    String imageUri = media.getPosterPath(ImageSize.W154);
                    String backdropUri = media.getBackdropPath(ImageSize.W200);
                    float rating = (float) (media.getVoteAverage() / 2.0);
                    Log.d("RATING", Float.toString(rating));
                    Glide.with(DetailActivity.this)
                            .load(imageUri)
                            .into(ivPoster);
                    Glide.with(DetailActivity.this)
                            .load(backdropUri)
                            .into(ivBackdrop);
                    tvName.setText(media.getTitle());
                    tvOverview.setText(media.getOverview());
                    rbRating.setRating(rating);
                    tvLabelEpisode.setVisibility(View.GONE);
                    tvLabelSeason.setVisibility(View.GONE);
                    tvLabelFirstAirDate.setVisibility(View.GONE);
                    tvLabelLastAirDate.setVisibility(View.GONE);
                    setActionBar(media.getTitle());
                }

                @Override
                public void onFailure(String message) {

                }
            });
        }
    }

    private void setActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


}