package com.example.zz.zz.model;

/**
 * Created by Pavel on 23.05.2018.
 */

public class AddSpecUserReview {
    private int idSpecUser;
    private int idReview;

    public AddSpecUserReview(int idSpecUser,int idReview){
        this.idSpecUser=idSpecUser;
        this.idReview=idReview;
    }

    public void setIdSpecUser(int idSpecUser) {
        this.idSpecUser = idSpecUser;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getIdSpecUser() {
        return idSpecUser;
    }

    public int getIdReview() {
        return idReview;
    }
}
