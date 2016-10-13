package com.brstdev.ideafaris.model;

import android.net.Uri;

import java.net.URL;

/**
 * Created by brst-pc16 on 9/23/16.
 */
public class FirebaseUserData {
    public FirebaseUserData(String username, String email, String profileUrl, String time, String phoneNum, String location) {
        this.username = username;
        this.email = email;
        this.profileUrl = profileUrl;
        this.time = time;
        this.phoneNum = phoneNum;
        this.location = location;


    }

    public FirebaseUserData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String username;
    String email;

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    String profileUrl;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String phoneNum;
    String location;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    String gender;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    String providerId;

    public FirebaseUserData(Uri photoUri, String username, String email) {
        this.photoUri = photoUri;
        this.email = email;
        this.username = username;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    Uri photoUri;
}
