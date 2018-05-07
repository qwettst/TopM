package com.example.zz.zz.model.getSpecUser;

import com.example.zz.zz.model.getAllReview.GetReview;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pavel on 07.05.2018.
 */

public class GetSpecUser {
    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otchestvo")
    @Expose
    private String otchestvo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("onCall")
    @Expose
    private Integer onCall;
    @SerializedName("specName")
    @Expose
    private String specName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("reviews")
    @Expose
    private List<GetReview> reviews = null;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOnCall() {
        return onCall;
    }

    public void setOnCall(Integer onCall) {
        this.onCall = onCall;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<GetReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<GetReview> reviews) {
        this.reviews = reviews;
    }
}
