package com.example.zz.zz.model;

import com.example.zz.zz.model.getAllReview.ReviewsParameter;
import com.example.zz.zz.model.getAllReview.Spec;
import com.example.zz.zz.model.getAllReview.User;

import java.util.List;

/**
 * Created by Pavel on 03.04.2018.
 */

public class AllReviewData {
    public AllReviewData(){

    }

    private int status;

    private int idReview;

    private User user;

    private String surname;

    private String name;

    private String otchestvo;

    private String spec;

    private String city;

    private String address;

    private String datetime;

    private String content;

    private List<ReviewsParameter> reviewsParameters = null;

    private Integer idSpecUser;



    public void setUser(User user) {
        this.user = user;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReviewsParameters(List<ReviewsParameter> reviewsParameters) {
        this.reviewsParameters = reviewsParameters;
    }




    public User getUser() {
        return user;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public String getSpec() {
        return spec;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getContent() {
        return content;
    }

    public List<ReviewsParameter> getReviewsParameters() {
        return reviewsParameters;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getIdReview() {
        return idReview;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setIdSpecUser(Integer idSpecUser) {
        this.idSpecUser = idSpecUser;
    }

    public Integer getIdSpecUser() {
        return idSpecUser;
    }
}
