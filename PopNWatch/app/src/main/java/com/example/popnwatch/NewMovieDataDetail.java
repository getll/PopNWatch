package com.example.popnwatch;

public class NewMovieDataDetail {
    public String id;
    public String title; //include
    public String year;

    public String image;  //include
    public String runtimeMins; //include
    public String plot;

    public String contentRating; //include
    public String imDbRating; //include

    public String genres; //include
    public String directors;
    public String stars;

    public NewMovieDataDetail() {
    }

    public NewMovieDataDetail(String id, String title, String year, String image, String runtimeMins, String plot, String contentRating, String imDbRating, String genres, String directors, String stars) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.image = image;
        this.runtimeMins = runtimeMins;
        this.plot = plot;
        this.contentRating = contentRating;
        this.imDbRating = imDbRating;
        this.genres = genres;
        this.directors = directors;
        this.stars = stars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(String runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }
}
