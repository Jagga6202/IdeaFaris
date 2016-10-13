package com.brstdev.ideafaris.model;

/**
 * Created by brst-pc16 on 9/29/16.
 */
public class WriteStoryFirebase {
    public WriteStoryFirebase(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public WriteStoryFirebase() {
    }

    public WriteStoryFirebase(String name, String title, String profilepic) {
        this.name = name;
        this.title = title;
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String name;
    String title;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    String profilepic;


}
