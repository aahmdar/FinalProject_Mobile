package com.aahmdar.finalmobile.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.aahmdar.finalmobile.R;
import com.aahmdar.finalmobile.ui.fragments.FavoriteFragment;
import com.aahmdar.finalmobile.ui.fragments.MainFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView botNav = findViewById(R.id.bnv_main);
        botNav.setOnNavigationItemSelectedListener(this);
        setSelectedItem(botNav);
    }

    private void setSelectedItem(BottomNavigationView bottomNavigationView) {
        if (getIntent().getStringExtra("SELECTED_FRAGMENT") != null) {
            switch (getIntent().getStringExtra("SELECTED_FRAGMENT")) {
                case "tv_show":
                    bottomNavigationView.setSelectedItemId(R.id.item_tvshow);
                    break;
                case "movie":
                    bottomNavigationView.setSelectedItemId(R.id.item_movie);
                    break;
                case "favorite":
                    bottomNavigationView.setSelectedItemId(R.id.item_favorite);
                    break;
            }
        } else {
            bottomNavigationView.setSelectedItemId(R.id.item_tvshow);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        String sortBy = null;
        switch (menuItem.getItemId()) {
            case R.id.item_tvshow:
                setActionBar(getString(R.string.tvshow), R.drawable.ic_tvshow);
                sortBy = "tv_show";;
                fragment = new MainFragment();
                break;
            case R.id.item_movie:
                setActionBar(getString(R.string.movie), R.drawable.ic_movie);
                // TODO: pass filter
                sortBy = "movie";
                fragment = new MainFragment();
                break;
            case R.id.item_favorite:
                setActionBar(getString(R.string.favorite), R.drawable.ic_favorite_white);
                fragment = new FavoriteFragment();
                break;
        }
        if (fragment != null) {
            // Method that handle which data to show base on @sortBy params
            startFragment(fragment, sortBy);
            return true;
        }
        return false;
    }

    private void setActionBar(String title, int logo) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("\t" + title);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setLogo(logo);
        }
    }

    private void startFragment(Fragment fragment, String bundle) {
        if (bundle != null) {
            Bundle sortBy = new Bundle();
            sortBy.putString("SORT_BY", bundle);
            fragment.setArguments(sortBy);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_main, fragment)
                .commit();
    }

}