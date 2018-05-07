package com.example.zz.zz.model;

/**
 * Created by Pavel on 02.05.2018.
 */

public class UserProfile_DB {

    public static final String TABLE_NAME = "user_profile";

    public static final String COLUMN_ID = "idU";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_EMAIL = "email";

    private Integer idU;
    private String firstname;
    private String lastname;
    private String email;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " TEXT,"
                    + COLUMN_FIRSTNAME + " TEXT,"
                    + COLUMN_LASTNAME + " TEXT,"
                    + COLUMN_EMAIL + " TEXT"
                    + ")";
    public UserProfile_DB(){

    }

    public UserProfile_DB(Integer idU, String firstname, String lastname, String email){
        this.idU=idU;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
    }

    public void setIdU(Integer idU) {
        this.idU = idU;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getIdU() {
        return idU;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
