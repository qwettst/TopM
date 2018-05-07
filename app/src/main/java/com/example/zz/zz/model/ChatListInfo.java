package com.example.zz.zz.model;

/**
 * Created by Pavel on 23.04.2018.
 */

public class ChatListInfo {
    private String sender;
    private String mesText;

    public  ChatListInfo(){}

    public ChatListInfo(String sender, String mesText) {
        this.sender = sender;
        this.mesText= mesText;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMesText() {
        return mesText;
    }

    public void setMesText(String mesText) { this.mesText = mesText; }
}
