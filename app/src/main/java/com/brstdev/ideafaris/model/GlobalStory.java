package com.brstdev.ideafaris.model;

import android.net.Uri;

import java.net.URL;

/**
 * Created by brst-pc16 on 9/12/16.
 */
public class GlobalStory {
    private String title;
    private String genre;
    private String year;
    String phoneNum;
    String location;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    String gender;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public GlobalStory(String genre, String year, String profileUrl, String time, String phoneNum, String location) {

        this.genre = genre;
        this.year = year;
        this.profileUrl = profileUrl;
        this.time = time;
        this.phoneNum = phoneNum;
        this.location = location;

    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    private String profileUrl;

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public GlobalStory(String title, String genre, String year, Uri photoUri) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.photoUri = photoUri;
    }

    Uri photoUri;

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    private int imageUrl;

    public GlobalStory() {
    }

    public GlobalStory(String title, String genre, String year, int imageUrl) {
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