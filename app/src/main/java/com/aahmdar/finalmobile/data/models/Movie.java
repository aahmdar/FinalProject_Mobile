package com.aahmdar.finalmobile.data.models;

import com.google.gson.annotations.SerializedName;
import com.aahmdar.finalmobile.Consts;
import com.aahmdar.finalmobile.ImageSize;
import com.aahmdar.finalmobile.data.models.Genre;

import java.util.List;

public class Movie {

    @SerializedName("original_title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("vote_average")
    private float voteAverage;

    public float getVoteAverage() {
        return voteAverage;
    }

    public float getPopularity() {
        return popularity;
    }

    private int id;

    @SerializedName("genres")
    private List<Genre> genres;

    private String overview;

    public String getTitle() {
        return title;
    }


    public String getBackdropPath(ImageSize size) {
        return Consts.IMG_URL + size.getValue() + backdropPath;
    }


    public String getPosterPath(ImageSize size) {
        return Consts.IMG_URL + size.getValue() + posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Genre> getGenres() {
        return genres;
    }


    public String getOverview() {
        return overview;
    }

}