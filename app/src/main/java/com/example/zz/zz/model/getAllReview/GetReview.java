package com.example.zz.zz.model.getAllReview;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetReview {

    @SerializedName("idReview")
    @Expose
    private Integer idReview;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otchestvo")
    @Expose
    private String otchestvo;
    @SerializedName("specName")
    @Expose
    private String specName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("onCall")
    @Expose
    private Integer onCall;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("reviewsParameters")
    @Expose
    private List<ReviewsParameter> reviewsParameters = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    private Integer idSpecUser;

    public Integer getIdReview() {
        return idReview;
    }

    public void setIdReview(Integer idReview) {
        this.idReview = idReview;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Integer getOnCall() {
        return onCall;
    }

    public void setOnCall(Integer onCall) {
        this.onCall = onCall;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ReviewsParameter> getReviewsParameters() {
        return reviewsParameters;
    }

    public void setReviewsParameters(List<ReviewsParameter> reviewsParameters) {
        this.reviewsParameters = reviewsParameters;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setIdSpecUser(Integer idSpecUser) {
        this.idSpecUser = idSpecUser;
    }

    public Integer getIdSpecUser() {
        return idSpecUser;
    }
}
