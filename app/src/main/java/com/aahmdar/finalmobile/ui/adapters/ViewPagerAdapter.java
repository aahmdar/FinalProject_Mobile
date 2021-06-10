package com.aahmdar.finalmobile.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aahmdar.finalmobile.ui.fragments.FavoriteMovieFragment;
import com.aahmdar.finalmobile.ui.fragments.FavoriteTvFragment;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final Fragment[] fragments;

    public ViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new Fragment[]{
                new FavoriteMovieFragment(),
                new FavoriteTvFragment()
        };
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }
}