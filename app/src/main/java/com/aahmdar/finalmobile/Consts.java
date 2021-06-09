package com.aahmdar.finalmobile;

import java.util.Locale;

public class Consts {
    public static final String API_KEY = "dadd178945b1d1347da528fdec988720";
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String IMG_URL = "https://image.tmdb.org/t/p/";

    public static String getLang() {
        switch (Locale.getDefault().toString()) {
            case "in_ID":
                return "id-ID";
            default:
                return "en-US";
        }
    }
}
