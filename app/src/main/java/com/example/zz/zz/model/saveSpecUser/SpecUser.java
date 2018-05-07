package com.example.zz.zz.model.saveSpecUser;


/**
 * Created by Pavel on 07.05.2018.
 */

public class SpecUser {

    private Long idUser;

    private String surname;


    private String name;

    private String otchestvo;


    private String email;


    private Integer onCall;


    private String specName;


    private String city;


    private String address;

    private String info;


    public SpecUser() {
    }

    public SpecUser(Long idUser, String surname, String name, String otchestvo, String email, Integer onCall, String specName, String city, String address, String info) {
        this.idUser = idUser;
        this.surname = surname;
        this.name = name;
        this.otchestvo = otchestvo;
        this.email = email;
        this.onCall = onCall;
        this.specName = specName;
        this.city = city;
        this.address = address;
        this.info = info;

    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
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


}
