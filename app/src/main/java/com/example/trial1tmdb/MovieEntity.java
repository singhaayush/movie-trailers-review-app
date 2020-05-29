package com.example.trial1tmdb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieEntity  implements Parcelable {
    private int movieId;
    private String originalTitle;
    private String movieOverview;
    private int rating;
    private String posterImagePath;
    private ArrayList<Integer>generes;
    private String backdropImagePath;

    public MovieEntity(int movieId, String originalTitle, String movieOverview, int rating, String posterImagePath, ArrayList<Integer> generes, String backdropImagePath) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.movieOverview = movieOverview;
        this.rating = rating;
        this.posterImagePath = posterImagePath;
        this.generes = generes;
        this.backdropImagePath = backdropImagePath;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public int getRating() {
        return rating;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public ArrayList<Integer> getGeneres() {
        return generes;
    }

    public String getBackdropImagePath() {
        return backdropImagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
