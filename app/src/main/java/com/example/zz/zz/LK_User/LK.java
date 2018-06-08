package com.example.zz.zz.LK_User;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zz.zz.R;
import com.example.zz.zz.ReviewGetter;
import com.example.zz.zz.Show_Review.allReview;
import com.example.zz.zz.SpecUserGetter;
import com.example.zz.zz.adapter.ReviewInfo_LK_Adapter;
import com.example.zz.zz.chat.user_chat_list;
import com.example.zz.zz.database.DatabaseUserProfileHelper;
import com.example.zz.zz.model.ChatMessage;
import com.example.zz.zz.model.ChatUsers;
import com.example.zz.zz.model.UserProfile_DB;
import com.example.zz.zz.model.getAllReview.GetReview;
import com.example.zz.zz.model.getSpecUser.GetSpecUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LK extends Fragment {

    private RecyclerView recyclerView;
    private ReviewInfo_LK_Adapter ReviewInfo_LK_Adapter;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LK.OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Review.
     */
    // TODO: Rename and change types and number of parameters
    public static LK newInstance(String param1, String param2) {
        LK fragment = new LK();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        return fragment;
    }


    public LK() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_menu_lkspec, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView tvName,tvSpec,tvCity,tvAdress,tvAb,
    tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    private RatingBar rbRate;
    private ProgressBar pbTask;
    private int iUid;
    private List<GetReview> getReviewList = new ArrayList<>();
    private Bundle bundle;
    private String sNameSpec, suIDSpec;
    private DatabaseReference myRef;
    private DatabaseUserProfileHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lk, container,
                false);
        getActivity().setTitle("Личный кабинет");
        ImageView ivBackground;

        bundle = this.getArguments();

        iUid= bundle.getInt("uIDSpec");

        tvName=(TextView)rootView.findViewById(R.id.Uname);
        tvSpec=(TextView)rootView.findViewById(R.id.USpec);
        tvCity=(TextView)rootView.findViewById(R.id.UCity);
        tvAdress=(TextView)rootView.findViewById(R.id.UStreet);
        tvAb=(TextView)rootView.findViewById(R.id.uAb);
        pbTask=(ProgressBar)rootView.findViewById(R.id.progress);

        tv1=(TextView)rootView.findViewById(R.id.textView0);
        tv2=(TextView)rootView.findViewById(R.id.textView);
        tv3=(TextView)rootView.findViewById(R.id.textView4);
        tv4=(TextView)rootView.findViewById(R.id.uAb_tv);
        tv5=(TextView)rootView.findViewById(R.id.textView6);
        tv6=(TextView)rootView.findViewById(R.id.textView8);
        tv7=(TextView)rootView.findViewById(R.id.countRev);
        tv8=(TextView)rootView.findViewById(R.id.textView7);
        rbRate=(RatingBar)rootView.findViewById(R.id.uRate);


        ivBackground=(ImageView) rootView.findViewById(R.id.background_image);
        ivBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(this)
                .load(R.drawable.background)
                .into(ivBackground);



        getActivity().findViewById(R.id.search_form).setVisibility(View.GONE);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.revinfo_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        FragmentManager fragmentManager = getFragmentManager();
        ReviewInfo_LK_Adapter = new ReviewInfo_LK_Adapter(getReviewList,fragmentManager,bundle);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ReviewInfo_LK_Adapter);

        updateUI(0);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://94.251.14.36:8080/TopMaster/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpecUserGetter specUserGetter = retrofit.create(SpecUserGetter.class);

        final Call<GetSpecUser> review = specUserGetter.serviceUser(iUid);

        review.enqueue(new Callback<GetSpecUser>() {
            @Override
            public void onResponse(Call<GetSpecUser> call, Response<GetSpecUser> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG","response " + response.body());
                    sNameSpec=response.body().getName()+" "+response.body().getSurname()+" "+response.body().getOtchestvo();
                    tvName.setText(sNameSpec);
                    tvSpec.setText(response.body().getSpecName());
                    tvAb.setText(response.body().getInfo());
                    tvAdress.setText(response.body().getAddress());
                    tvCity.setText(response.body().getCity());

                    getReviewList.addAll(response.body().getReviews());
                    suIDSpec=response.body().getEmail();

                    ReviewInfo_LK_Adapter.notifyDataSetChanged();
                    rbRate.setRating(ReviewInfo_LK_Adapter.getRateSpec());
                    tv7.setText(String.valueOf(ReviewInfo_LK_Adapter.getItemCount()));
                    updateUI(1);
                } else {
                    Log.d("TAG","response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSpecUser> call, Throwable t) {
                Log.d("Tag","failure " + t);
                Toasty.error(getContext(), "ЛК не существует", Toast.LENGTH_SHORT, true).show();
            }
        });


        TextView countRev=(TextView)rootView.findViewById(R.id.countRev);

        countRev.setText(Integer.toString(ReviewInfo_LK_Adapter.getItemCount()));


        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lk_sendMes: {
                UserProfile_DB uDB;
                db = new DatabaseUserProfileHelper(getContext());
                uDB = db.getUserById(bundle.getInt("uID"));

                if (!uDB.getEmail().equals(suIDSpec)) {
                    bundle.putString("childArg", sNameSpec);
                    ChatUsers chatUsers = new ChatUsers();
                    chatUsers.setChatName(sNameSpec);
                    chatUsers.setChatID(suIDSpec);
                    myRef = FirebaseDatabase.getInstance().getReference();
                    myRef.child("Users").child(uDB.getEmail()).child("ChatList").child("OpenChats").child(suIDSpec).
                            setValue(chatUsers);

                    chatUsers.setChatName(uDB.getFirstname() + " " + uDB.getLastname());
                    chatUsers.setChatID(uDB.getEmail());
                    myRef.child("Users").child(suIDSpec).child("ChatList").child("OpenChats").child(uDB.getEmail()).
                            setValue(chatUsers);
                    Class fragmentClass;
                    fragmentClass = user_chat_list.class;
                    Fragment myFragment = null;
                    FragmentManager mFragment = getActivity().getSupportFragmentManager();
                    try {
                        myFragment = (Fragment) fragmentClass.newInstance();
                        myFragment.setArguments(bundle);
                        mFragment.beginTransaction().replace(R.id.flcontent, myFragment).commit();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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




    void updateUI(int a){
        switch (a) {
            case 1:
                tvName.setVisibility(View.VISIBLE);
                tvSpec.setVisibility(View.VISIBLE);
                tvCity.setVisibility(View.VISIBLE);
                tvAdress.setVisibility(View.VISIBLE);
                tvAb.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);


                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.VISIBLE);
                tv6.setVisibility(View.VISIBLE);
                tv7.setVisibility(View.VISIBLE);
                tv8.setVisibility(View.VISIBLE);

                rbRate.setVisibility(View.VISIBLE);

                pbTask.setVisibility(View.GONE);

                break;
            case 0:
                tvName.setVisibility(View.GONE);
                tvSpec.setVisibility(View.GONE);
                tvCity.setVisibility(View.GONE);
                tvAdress.setVisibility(View.GONE);
                tvAb.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                tv5.setVisibility(View.GONE);
                tv6.setVisibility(View.GONE);
                tv7.setVisibility(View.GONE);
                tv8.setVisibility(View.GONE);
                rbRate.setVisibility(View.GONE);

                pbTask.setVisibility(View.VISIBLE);

                break;
        }

    }




}
