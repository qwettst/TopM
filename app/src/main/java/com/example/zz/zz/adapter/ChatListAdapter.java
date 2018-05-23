package com.example.zz.zz.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.zz.zz.R;


import com.example.zz.zz.chat.message_list;
import com.example.zz.zz.model.ChatListInfo;


import java.util.List;

/**
 * Created by Pavel on 23.04.2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private List<ChatListInfo> chatListInfoList;
    private FragmentManager mFragment;
    private Bundle bundle;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView sender, mesText;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            sender = (TextView) view.findViewById(R.id.chat_userName);
            mesText = (TextView) view.findViewById(R.id.chat_userText);
        }
        @Override
        public void onClick(View view) {

            int position = getLayoutPosition();
            ChatListInfo chatListInfo = chatListInfoList.get(position);

            Class fragmentClass;
            fragmentClass=message_list.class;
            Fragment myFragment=null;
            try {
                myFragment=(Fragment)fragmentClass.newInstance();
                myFragment.setArguments(bundle);
                mFragment.beginTransaction().replace(R.id.flcontent, myFragment).commit();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }
    public ChatListAdapter(List<ChatListInfo> chatListInfoList,FragmentManager fragment, Bundle bundle) {
        this.chatListInfoList = chatListInfoList;
        mFragment  = fragment;
        this.bundle=bundle;
    }

    @Override
    public ChatListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_chat_list, parent, false);
        return new ChatListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ChatListInfo chatListInfo = chatListInfoList.get(position);
        holder.sender.setText(chatListInfo.getSender()+" ");
        holder.mesText.setText(chatListInfo.getMesText());
    }



    @Override
    public int getItemCount() {
        return chatListInfoList.size();
    }
}
