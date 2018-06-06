package com.example.zz.zz.model;

/**
 * Created by Pavel on 06.06.2018.
 */

public class ChatUsers {

    private String chatName;
    private String chatID;

    public ChatUsers(String chatName, String chatID) {
        this.chatName = chatName;
        this.chatID= chatID;
    }

    public ChatUsers(){

    }
    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getChatName() {
        return chatName;
    }

    public String getChatID() {
        return chatID;
    }
}
