package com.aahmdar.finalmobile.data.local;

import com.aahmdar.finalmobile.Consts;
import com.aahmdar.finalmobile.ImageSize;

import io.realm.RealmObject;
import com.aahmdar.finalmobile.Consts;
import com.aahmdar.finalmobile.ImageSize;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class FavoriteTv extends RealmObject {
    @PrimaryKey private int id;
    @Required private String title;
    private String posterPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath(ImageSize size) {
        return Consts.IMG_URL + size.getValue() + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}