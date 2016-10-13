package com.brstdev.ideafaris.model;

/**
 * Created by brst-pc16 on 9/13/16.
 */
public class FriendList {
    private String freindname;

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    private int imageUrl;

    public String getFreindemail() {
        return freindemail;
    }

    public void setFreindemail(String freindemail) {
        this.freindemail = freindemail;
    }

    public String getFreindname() {
        return freindname;
    }

    public void setFreindname(String freindname) {
        this.freindname = freindname;
    }

    private String freindemail;

    public FriendList() {
    }

    public FriendList(String freindname, String freindemail, int imageUrl) {
        this.freindname = freindname;
        this.freindemail = freindemail;
        this.imageUrl = imageUrl;

    }


}