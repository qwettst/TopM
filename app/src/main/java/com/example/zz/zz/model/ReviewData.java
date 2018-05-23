package com.example.zz.zz.model;

/**
 * Created by Pavel on 09.04.2018.
 */

public class ReviewData {

    public ReviewData(){

    }

    private String author;
    private String nameMaster;
    private String spec;
    private String city;
    private String street;
    private String dateReview;
    private String tReview;
    private int pos;
    private int idReview;

    public String getAuthor() {
        return author;
    }

    public String getNameMaster() {
        return nameMaster;
    }

    public String getSpec() {
        return spec;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getDateReview() {
        return dateReview;
    }

    public String gettReview() {return tReview;}

    public int getPos() {return pos;}




    public void setAuthor(String author){
        this.author=author;

    }
    public void setNameMaster(String nameMaster){
        this.nameMaster=nameMaster;

    }
    public void setSpec(String spec){
        this.spec=spec;

    }
    public void setCity(String city){
        this.city=city;

    }
    public void setStreet(String street){
        this.street=street;

    }
    public void setDateReview(String dateReview){
        this.dateReview=dateReview;

    }
    public void setTReview(String tReview){

        this.tReview=tReview;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getIdReview() {
        return idReview;
    }
}
