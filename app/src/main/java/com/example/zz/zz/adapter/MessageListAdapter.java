package com.example.zz.zz.adapter;




import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zz.zz.R;
import com.example.zz.zz.model.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by Pavel on 23.04.2018.
 */

public class MessageListAdapter extends  RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    private List<ChatMessage> mMessageList;
    private String sUserName;



    public MessageListAdapter( List<ChatMessage> messageList, String sUserName) {
        this.sUserName=sUserName;
        mMessageList = messageList;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ChatMessage chatMessage= (ChatMessage) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(chatMessage);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(chatMessage);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = (ChatMessage) mMessageList.get(position);
        String sender=chatMessage.getMessageSender();


        if (sender.equals(sUserName)) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {

            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }





    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }
        void bind(ChatMessage chatMessage) {
            messageText.setText(chatMessage.getMessageText());

            // Format the stored timestamp into a readable String using method.
            timeText.setText( chatMessage.getMessageTime());
        }

    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, messageSender;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            messageSender = (TextView) itemView.findViewById(R.id.text_message_name);
        }
        void bind(ChatMessage chatMessage) {
            messageText.setText(chatMessage.getMessageText());
            messageSender.setText(chatMessage.getMessageSender());

            // Format the stored timestamp into a readable String using method.
            timeText.setText( chatMessage.getMessageTime());
        }

    }
}