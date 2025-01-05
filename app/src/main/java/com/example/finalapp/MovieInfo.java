package com.example.finalapp;

public class MovieInfo {

    String title, year, actor, country, plot, image, rating, genre, runtime, director, writer, imdbrating;


    public MovieInfo(String title, String year, String actor, String country, String plot, String image, String rating, String genre, String runtime, String director, String writer, String imdbrating) {
        this.title = title;
        this.year = year;
        this.actor = actor;
        this.country = country;
        this.plot = plot;
        this.image = image;
        this.rating = rating;
        this.genre = genre;
        this.runtime = runtime;
        this.director = director;
        this.writer = writer;
        this.imdbrating = imdbrating;

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

    public String getActor() {

        return actor;
    }

    public void setAuthor(String actor) {

        this.actor = actor;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public String getPlot() {

        return plot;
    }

    public void setPlot(String plot) {

        this.plot = plot;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public String getRating() {

        return rating;
    }

    public void setRating(String rating) {

        this.rating = rating;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {

        this.genre = genre;
    }

    public String getRuntime() {

        return runtime;
    }

    public void setRuntime(String runtime) {

        this.runtime = runtime;
    }

    public String getDirector() {

        return director;
    }

    public void setDirector(String director) {

        this.director = director;
    }

    public String getWriter() {

        return writer;
    }

    public void setWriter(String writer) {


        this.writer = writer;
    }

    public String getImdbrating() {

        return imdbrating;
    }

    public void setImdbrating(String imdbrating) {

        this.imdbrating = imdbrating;
    }
}
