package com.aahmdar.finalmobile.ui.adapters.clicklistener;

import com.aahmdar.finalmobile.data.models.Movie;
import com.aahmdar.finalmobile.data.models.TvShow;
import com.aahmdar.finalmobile.data.local.FavoriteMovie;
import com.aahmdar.finalmobile.data.local.FavoriteTv;

public interface OnItemClickListener {
    void onClick(TvShow tvShow);
    void onClick(Movie movie);
    void onClick(FavoriteMovie movie);
    void onClick(FavoriteTv tv);
}