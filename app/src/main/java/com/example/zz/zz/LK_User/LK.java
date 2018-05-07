package com.example.zz.zz.LK_User;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zz.zz.R;
import com.example.zz.zz.ReviewGetter;
import com.example.zz.zz.Show_Review.allReview;
import com.example.zz.zz.SpecUserGetter;
import com.example.zz.zz.adapter.ReviewInfo_LK_Adapter;
import com.example.zz.zz.model.getAllReview.GetReview;
import com.example.zz.zz.model.getSpecUser.GetSpecUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LK extends Fragment {

    private RecyclerView recyclerView;
    private ReviewInfo_LK_Adapter ReviewInfo_LK_Adapter;
    private List<reviewInfo_lk> reviewInfo_lkList = new ArrayList<>();


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

    private TextView tvName,tvSpec,tvCity,tvAdress,tvAb;
    private TaskGetReview tgrTask;
    private ProgressBar pbTask;
    private int iUID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lk, container,
                false);

        ImageView ivBackground;

        Bundle bundle = this.getArguments();

        iUID= bundle.getInt("uID");

        tvName=(TextView)rootView.findViewById(R.id.Uname);
        tvSpec=(TextView)rootView.findViewById(R.id.USpec);
        tvCity=(TextView)rootView.findViewById(R.id.UCity);
        tvAdress=(TextView)rootView.findViewById(R.id.UStreet);
        tvAb=(TextView)rootView.findViewById(R.id.uAb);
        pbTask=(ProgressBar)rootView.findViewById(R.id.progress);


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
        ReviewInfo_LK_Adapter = new ReviewInfo_LK_Adapter(reviewInfo_lkList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ReviewInfo_LK_Adapter);

        updateUI(0);

        tgrTask = new TaskGetReview();
        tgrTask.execute();


        TextView countRev=(TextView)rootView.findViewById(R.id.countRev);

        countRev.setText(Integer.toString(ReviewInfo_LK_Adapter.getItemCount()));


        return rootView;
    }


    class TaskGetReview extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbTask.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://94.251.14.36:8080/TopMaster/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SpecUserGetter specUserGetter = retrofit.create(SpecUserGetter.class);

            final Call<GetSpecUser> review = specUserGetter.serviceUser(iUID);

            review.enqueue(new Callback<GetSpecUser>() {
                @Override
                public void onResponse(Call<GetSpecUser> call, Response<GetSpecUser> response) {
                    if (response.isSuccessful()) {
                        Log.d("TAG","response " + response.body());
                        tvName.setText(response.body().getName()+" "+response.body().getSurname()+" "+response.body().getOtchestvo());
                        tvSpec.setText(response.body().getSpecName());
                        tvAb.setText(response.body().getInfo());
                        tvAdress.setText(response.body().getAddress());
                        tvCity.setText(response.body().getCity());
                    } else {
                        Log.d("TAG","response code " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<GetSpecUser> call, Throwable t) {
                    Log.d("Tag","failure " + t);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pbTask.setVisibility(View.GONE);
            updateUI(1);
        }
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


    private void reviewInfoData() {
        reviewInfo_lk reviewInfoLk = new reviewInfo_lk("Гость", "четко","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);

        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);

        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);
        reviewInfoLk = new reviewInfo_lk("Гость", "Красиво","19.03.18");
        reviewInfo_lkList.add(reviewInfoLk);


        ReviewInfo_LK_Adapter.notifyDataSetChanged();
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
                break;
            case 0:
                tvName.setVisibility(View.GONE);
                tvSpec.setVisibility(View.GONE);
                tvCity.setVisibility(View.GONE);
                tvAdress.setVisibility(View.GONE);
                tvAb.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                break;
        }

    }


}
