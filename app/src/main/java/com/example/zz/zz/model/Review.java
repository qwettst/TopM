package com.example.zz.zz.model;

/**
 * Created by Pavel on 08.04.2018.
 */

public class Review {

    public static final String TABLE_NAME = "review";

    public static final String COLUMN_ID = "idR";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_NAMEMASTER = "namemMster";
    public static final String COLUMN_SPEC = "spec";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_DATEREVIEW= "dateReview";
    public static final String COLUMN_TREVIEW= "tReview";
    public static final String COLUMN_REVRATE= "revRate";


    private int idR;
    private String author;
    private String nameMaster;
    private String spec;
    private String city;
    private String street;
    private String dateReview;
    private String tReview;
    private float fRate;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_AUTHOR + " TEXT,"
                    + COLUMN_NAMEMASTER + " TEXT,"
                    + COLUMN_SPEC + " TEXT,"
                    + COLUMN_CITY + " TEXT,"
                    + COLUMN_STREET + " TEXT,"
                    + COLUMN_DATEREVIEW + " TEXT,"
                    + COLUMN_TREVIEW + " TEXT,"
                    + COLUMN_REVRATE + " REAL"
                    + ")";
    public Review(){

    }

    public Review(int idR, String author, String nameMaster, String spec, String city, String street, String dateReview, String tReview, float fRate){
        this.idR=idR;
        this.author=author;
        this.nameMaster=nameMaster;
        this.spec=spec;
        this.city=city;
        this.street=street;
        this.dateReview=dateReview;
        this.tReview=tReview;
        this.fRate=fRate;
    }


    public int getIdR() {
        return idR;
    }

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

    public String gettReview() {
        return tReview;
    }

    public float getRevRate() {
        return fRate;
    }





    public void setIdR(int idR){
        this.idR=idR;

    }
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

    public void setRevRate(float fRate){
        this.fRate=fRate;

    }

}
