package com.aahmdar.finalmobile.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.aahmdar.finalmobile.R;
import com.aahmdar.finalmobile.ui.adapters.ViewPagerAdapter;


public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        ViewPager2 viewPager = rootView.findViewById(R.id.vp_fav);
        viewPager.setAdapter(new ViewPagerAdapter(getActivity()));

        TabLayout tabLayout = rootView.findViewById(R.id.tab_fav);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();


        (tabLayout.getTabAt(0)).setText("Movie");
        (tabLayout.getTabAt(1)).setText("TV Show");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 0) {
//                    Fragment fragment = new FavoriteMovieFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("SORT_BY", "movie");
//                    fragment.setArguments(bundle);
//
//                } else {
//                    Fragment fragment = new FavoriteTvFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("SORT_BY", "tv_show");
//                    fragment.setArguments(bundle);
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }


}