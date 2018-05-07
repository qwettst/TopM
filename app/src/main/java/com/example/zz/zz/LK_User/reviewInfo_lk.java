package com.example.zz.zz.LK_User;

/**
 * Created by Pavel on 08.04.2018.
 */


public class reviewInfo_lk {
    private String Author;
    private String shortReview;
    private String uDate;

    public reviewInfo_lk() {
    }

    public reviewInfo_lk(String author, String review, String udate) {
        Author = author;
        shortReview = review;
        uDate = udate;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getShortReview() {
        return shortReview;
    }

    public void setShortReview(String review) {
        shortReview = review;
    }

    public String getDate() {
        return uDate;
    }

    public void setDate(String udate) {
        uDate = udate;
    }
}