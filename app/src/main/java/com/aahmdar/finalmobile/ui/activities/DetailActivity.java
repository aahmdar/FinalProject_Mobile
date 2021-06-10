package com.aahmdar.finalmobile.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.aahmdar.finalmobile.ui.adapters.CastAdapter;
import com.aahmdar.finalmobile.ui.adapters.GenreAdapter;
import com.aahmdar.finalmobile.data.api.repository.callback.OnDetailCallBack;
import com.aahmdar.finalmobile.data.api.repository.callback.OnCastCallBack;
import com.aahmdar.finalmobile.data.local.FavoriteMovie;
import com.aahmdar.finalmobile.data.local.FavoriteTv;
import com.aahmdar.finalmobile.data.local.FavoriteHelper;
import com.aahmdar.finalmobile.data.models.Movie;
import com.aahmdar.finalmobile.data.models.TvShow;
import com.aahmdar.finalmobile.data.models.Cast;
import com.aahmdar.finalmobile.data.models.Genre;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

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
    private ArrayList<String> genres;
    private TvShowRepository tvRepo;
    private MovieRepository movieRepo;
    private RecyclerView rvGenre;
    private RecyclerView rvCast;
    private Realm backgroundThreadRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Realm.init(DetailActivity.this);

        String realmName = "final project";
        RealmConfiguration config = new RealmConfiguration.Builder().allowWritesOnUiThread(true).
                name(realmName).
                build();

        backgroundThreadRealm = Realm.getInstance(config);

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
        genres = new ArrayList<>();
        rvGenre = findViewById(R.id.rv_genre);
        rvCast = findViewById(R.id.rv_cast);
        tvRepo = TvShowRepository.getInstance();
        movieRepo = MovieRepository.getInstance();
    }

    private FavoriteMovie filterFavMovieById(int id) {
        RealmQuery<FavoriteMovie> query = backgroundThreadRealm.where(FavoriteMovie.class);
        query.equalTo("id", getIntent().getIntExtra("ID", 0));

        FavoriteMovie favMovie = query.findFirst();

        return favMovie;
    }

    private FavoriteTv filterFavTvById(int id) {
        RealmQuery<FavoriteTv> query = backgroundThreadRealm.where(FavoriteTv.class);
        query.equalTo("id", getIntent().getIntExtra("ID", 0));

        FavoriteTv favTv = query.findFirst();

        return favTv;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_detail_activity, menu);

        // TODO: switch favourite button state
        String selectedFragment = getIntent().getStringExtra("SELECTED_FRAGMENT");
        if (selectedFragment.equals("movie")) {
            FavoriteMovie favMovie = filterFavMovieById(getIntent().getIntExtra("ID", 0));
            if (favMovie != null) {
                menu.findItem(R.id.item_favorite).setIcon(R.drawable.ic_favorite_checked);
            }
        } else {
            FavoriteTv favTv = filterFavTvById(getIntent().getIntExtra("ID", 0));
            if (favTv != null) {
                menu.findItem(R.id.item_favorite).setIcon(R.drawable.ic_favorite_checked);
            }
        }
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
                if (getIntent().getStringExtra("SELECTED_FRAGMENT").equals("movie")) {
                    FavoriteMovie favMovie = filterFavMovieById(getIntent().getIntExtra("ID", 0));

                    if (favMovie != null) {

                        // TODO: delete favMovie here
                        backgroundThreadRealm.executeTransaction(transactionRealm -> {
                            favMovie.deleteFromRealm();
                        });
                        item.setIcon(R.drawable.ic_favorite_unchecked);
                    } else {
                        FavoriteMovie movie = new FavoriteMovie();
                        String title = getIntent().getStringExtra("TITLE");
                        String posterPath = getIntent().getStringExtra("POSTER_PATH");
                        int id = getIntent().getIntExtra("ID", 0);
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setPosterPath(posterPath);

                        backgroundThreadRealm.executeTransaction (transactionRealm -> transactionRealm.insert(movie));
                        item.setIcon(R.drawable.ic_favorite_checked);
                    }
                } else {
                    FavoriteTv favTv = filterFavTvById(getIntent().getIntExtra("ID", 0));

                    if (favTv != null) {

                        // TODO: delete favMovie here
                        backgroundThreadRealm.executeTransaction(transactionRealm -> {
                            favTv.deleteFromRealm();
                        });
                        item.setIcon(R.drawable.ic_favorite_unchecked);
                    } else {
                        FavoriteTv tv = new FavoriteTv();
                        String title = getIntent().getStringExtra("TITLE");
                        String posterPath = getIntent().getStringExtra("POSTER_PATH");
                        int id = getIntent().getIntExtra("ID", 0);
                        tv.setId(id);
                        tv.setTitle(title);
                        tv.setPosterPath(posterPath);

                        backgroundThreadRealm.executeTransaction (transactionRealm -> transactionRealm.insert(tv));
                        item.setIcon(R.drawable.ic_favorite_checked);
                    }
                }
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
        String selectedFragment = getIntent().getStringExtra("SELECTED_FRAGMENT");
        load(id, selectedFragment);
    }

    private void load(Integer id, String selectedFragment) {
        if (selectedFragment.equals("tv_show")) {
            tvRepo.getModelDetail(id, new OnDetailCallBack<TvShow>() {
                @Override
                public void onSuccess(TvShow media, String message) {
                    String imageUri = media.getPosterPath(ImageSize.W154);
                    String backdropUri = media.getBackdropPath(ImageSize.W200);
                    float rating = (float) (media.getVoteAverage() / 2.0);
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
                    setGenres(media.getGenres());
                    rvGenre.setLayoutManager(new LinearLayoutManager(DetailActivity.this, RecyclerView.HORIZONTAL, false));
                    rvGenre.setAdapter(new GenreAdapter(genres, DetailActivity.this));
                    loadCastData(id, selectedFragment);
                    setActionBar(media.getName());
                }

                @Override
                public void onFailure(String message) {

                }
            });
        } else {
            movieRepo.getModelDetail(id, new OnDetailCallBack<Movie>() {
                @Override
                public void onSuccess(Movie media, String message) {
                    String imageUri = media.getPosterPath(ImageSize.W154);
                    String backdropUri = media.getBackdropPath(ImageSize.W200);
                    float rating = (float) (media.getVoteAverage() / 2.0);
                    Glide.with(DetailActivity.this)
                            .load(imageUri)
                            .into(ivPoster);
                    Glide.with(DetailActivity.this)
                            .load(backdropUri)
                            .into(ivBackdrop);
                    tvName.setText(media.getTitle());
                    tvOverview.setText(media.getOverview());
                    rbRating.setRating(rating);
                    tvLabelEpisode.setVisibility(View.VISIBLE);
                    tvLabelSeason.setVisibility(View.VISIBLE);
                    tvLabelFirstAirDate.setVisibility(View.VISIBLE);
                    tvLabelLastAirDate.setVisibility(View.VISIBLE);
                    setGenres(media.getGenres());
                    rvGenre.setLayoutManager(new LinearLayoutManager(DetailActivity.this, RecyclerView.HORIZONTAL, false));
                    rvGenre.setAdapter(new GenreAdapter(genres, DetailActivity.this));
                    loadCastData(id, selectedFragment);
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

    private void setGenres(List<Genre> genresList){
        for(int i = 0; i< genresList.size(); i++){
            genres.add(genresList.get(i).getName());
        }
    }

    private void loadCastData(int id, String type) {
        if (type.equals("movie")) {
            movieRepo.getCasts(id, new OnCastCallBack() {
                @Override
                public void onSuccess(List<Cast> castList, String message) {
                    rvCast.setLayoutManager(new LinearLayoutManager(DetailActivity.this, RecyclerView.HORIZONTAL, false));
                    rvCast.setAdapter(new CastAdapter(castList, DetailActivity.this));
                }

                @Override
                public void onFailure(String message) {
                    Log.d("Error Fetching Cast", message);
                }
            });
        } else {
            tvRepo.getCasts(id, new OnCastCallBack() {
                @Override
                public void onSuccess(List<Cast> castList, String message) {
                    rvCast.setLayoutManager(new LinearLayoutManager(DetailActivity.this, RecyclerView.HORIZONTAL, false));
                    rvCast.setAdapter(new CastAdapter(castList, DetailActivity.this));
                }

                @Override
                public void onFailure(String message) {
                    Log.d("Error Fetching Cast", message);
                }
            });
        }
    }
}