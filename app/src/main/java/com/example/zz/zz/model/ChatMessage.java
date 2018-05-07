package com.example.zz.zz.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pavel on 23.04.2018.
 */

public class ChatMessage {

    private String messageText;
    private String messageSender;
    private String messageTime;


    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageSender= messageUser;
        DateFormat df = new SimpleDateFormat("dd.MM.yy");
        // Initialize to current time
        messageTime = df.format(Calendar.getInstance().getTime());
    }

    public ChatMessage(){

    }



    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageUser) {
        this.messageSender = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}