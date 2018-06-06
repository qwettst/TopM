package com.example.zz.zz.model;

/**
 * Created by Pavel on 23.04.2018.
 */

public class ChatListInfo {
    private ChatUsers chatUsers;

    public  ChatListInfo(){}

    public ChatListInfo(ChatUsers chatUsers) {
        this.chatUsers =chatUsers;
    }

    public void setChatUsers(ChatUsers chatUsers) {
        this.chatUsers = chatUsers;
    }

    public ChatUsers getChatUsers() {
        return chatUsers;
    }
}
