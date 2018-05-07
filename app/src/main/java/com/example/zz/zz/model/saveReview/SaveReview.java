package com.example.zz.zz.model.saveReview;

import com.example.zz.zz.model.getAllReview.User;

import java.util.List;

/**
 * Created by Pavel on 07.05.2018.
 */

public class SaveReview {

    private User user;
    private String surname;
    private String name;
    private String otchestvo;
    private String specName;
    private String city;
    private String address;
    private Integer onCall;
    private String content;
    private List<ParametrRate> reviewsParameters = null;

    public SaveReview(){}


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

    public String getSpecName() {
        return specName;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Integer getOnCall() {
        return onCall;
    }

    public String getContent() {
        return content;
    }

    public List<ParametrRate> getReviewsParameters() {
        return reviewsParameters;
    }




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

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOnCall(Integer onCall) {
        this.onCall = onCall;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReviewsParameters(List<ParametrRate> reviewsParameters) {
        this.reviewsParameters = reviewsParameters;
    }


}
