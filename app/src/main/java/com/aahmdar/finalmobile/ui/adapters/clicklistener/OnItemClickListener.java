package com.aahmdar.finalmobile.ui.adapters.clicklistener;

import com.aahmdar.finalmobile.data.models.Movie;
import com.aahmdar.finalmobile.data.models.TvShow;

public interface OnItemClickListener {
    void onClick(TvShow tvShow);
    void onClick(Movie movie);
}