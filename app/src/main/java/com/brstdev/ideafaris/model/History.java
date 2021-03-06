package com.brstdev.ideafaris.model;

/**
 * Created by brst-pc16 on 9/13/16.
 */
public class History {
    private String title, genre, year;

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    private int imageUrl;

    public History() {
    }

    public History(String title, String genre, String year, int imageUrl) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}