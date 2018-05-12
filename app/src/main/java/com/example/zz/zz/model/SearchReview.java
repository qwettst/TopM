package com.example.zz.zz.model;

/**
 * Created by Pavel on 12.05.2018.
 */

public class SearchReview {
    private String name;
    private String datetime;
    private String spec;
    private int onCall;
    private String city;
    private String adress;
    private float rate;

    public SearchReview(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSpec() {
        return spec;
    }

    public void setOnCall(int onCall) {
        this.onCall = onCall;
    }

    public int getOnCall() {
        return onCall;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdress() {
        return adress;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }
}
