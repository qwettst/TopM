package com.example.zz.zz.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.zz.zz.R;
import com.example.zz.zz.adapter.MessageListAdapter;
import com.example.zz.zz.model.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link message_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link message_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class message_list extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public message_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment message_list.
     */
    // TODO: Rename and change types and number of parameters
    public static message_list newInstance(String param1, String param2) {
        message_list fragment = new message_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ImageButton sendMessage;
    private EditText etMessage;
    private List<ChatMessage> chatMessageList = new ArrayList<>();
    private RecyclerView messeageRecyclerView;
    private MessageListAdapter messageListAdapter;


    private DatabaseReference myRef;
    private ChildEventListener mChildEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_message_list, container,
                false);
        sendMessage=rootView.findViewById(R.id.button_chatbox_send);
        etMessage=rootView.findViewById(R.id.edittext_chatbox);
        messeageRecyclerView=rootView.findViewById(R.id.reyclerview_message_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        messeageRecyclerView.setLayoutManager(mLayoutManager);


        myRef = FirebaseDatabase.getInstance().getReference();

        messageListAdapter=new MessageListAdapter(chatMessageList);
        messeageRecyclerView.setAdapter(messageListAdapter);

        if(mChildEventListener==null){
            mChildEventListener= new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage=null;
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                     chatMessage = messageSnapshot.getValue(ChatMessage.class);
                    }
                    chatMessageList.add(chatMessage);
                    messageListAdapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Не смогли установить onDisconnect:" + databaseError.getMessage());

                }
            };

            myRef.addChildEventListener(mChildEventListener);

        }

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser sender=FirebaseAuth.getInstance().getCurrentUser();
                if (etMessage.getText().length() != 0) {
                    ChatMessage chatMessage=new ChatMessage(etMessage.getText().toString(),sender.getUid());
                    myRef.push().child("Messages").
                            setValue(chatMessage);
                    etMessage.setText("");
                }
            }
        });
        return rootView;
    }
    @Override
    public void  onDestroy(){
        if(mChildEventListener!=null){
            myRef.removeEventListener(mChildEventListener);
            mChildEventListener=null;
            super.onDestroy();
        }
    }







    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
