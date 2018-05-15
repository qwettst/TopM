package com.example.zz.zz.Show_Review;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zz.zz.DataSendFragment;
import com.example.zz.zz.R;
import com.example.zz.zz.ReviewGetter;
import com.example.zz.zz.adapter.GetAllReview_Adapter;
import com.example.zz.zz.model.SearchReview;
import com.example.zz.zz.model.getAllReview.GetReview;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link allReview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link allReview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allReview extends Fragment  implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public allReview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment allReview.
     */
    // TODO: Rename and change types and number of parameters
    public static allReview newInstance(String param1, String param2) {
        allReview fragment = new allReview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView rvReviewView;
    private GetAllReview_Adapter getAllReviewAdapter;
    private List<GetReview> reviewInfo;
    private ProgressBar pbTask;
    private CheckBox chY,chN;
    private Button bSearch;
    private Bundle bundle;
    private DataSendFragment dataFromActivityToFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_review, container,
                false);

        getActivity().setTitle("Отзывы");

        ImageView ivBackground;

        ivBackground=(ImageView) rootView.findViewById(R.id.background_image);
        ivBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        reviewInfo=new ArrayList<>();

        Glide
                .with(this)
                .load(R.drawable.background)
                .into(ivBackground);

        getActivity().findViewById(R.id.search_form).setVisibility(View.GONE);

        bSearch=getActivity().findViewById(R.id.s_start);
        bSearch.setOnClickListener(this);


        chY=getActivity().findViewById(R.id.checkBoxY);
        chN=getActivity().findViewById(R.id.checkBoxN);
        chY.setOnClickListener(this);
        chN.setOnClickListener(this);
        chN.setChecked(true);



        rvReviewView = (RecyclerView) rootView.findViewById(R.id.all_review_recyler);
        pbTask=(ProgressBar)rootView.findViewById(R.id.progress);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        FragmentManager fragmentManager = getFragmentManager();
        getAllReviewAdapter = new GetAllReview_Adapter(reviewInfo,fragmentManager);
        rvReviewView.setLayoutManager(mLayoutManager);
        rvReviewView.setItemAnimator(new DefaultItemAnimator());
        rvReviewView.setAdapter(getAllReviewAdapter);




        bundle = this.getArguments();
        String strSign= bundle.getString("mSign");
        if(!strSign.equals("guest")) {
            FloatingActionButton fab = rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getActivity().findViewById(R.id.search_form).setVisibility(View.GONE);
                    Bundle bSReview =new Bundle();
                    if(bundle.getInt("uID")!=-1) {
                        bSReview.putString("mSign",bundle.getString("mSign"));
                        bSReview.putInt("uID", bundle.getInt("uID"));
                        Class fragmentClass;
                        fragmentClass = SendReview.class;
                        try {
                            Fragment myFragment = (Fragment) fragmentClass.newInstance();
                            myFragment.setArguments(bSReview);
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
                        } catch (java.lang.InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        else {
            FloatingActionButton fab = rootView.findViewById(R.id.fab);
            fab.setVisibility(View.GONE);
        }

        pbTask.setVisibility(View.VISIBLE);
        rvReviewView.setVisibility(View.GONE);


        Retrofit rftiReview = new Retrofit.Builder()
                .baseUrl("http://94.251.14.36:8080/TopMaster/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReviewGetter reviewGetter = rftiReview.create(ReviewGetter.class);

        final Call<List<GetReview>> review = reviewGetter.serviceReview();

        review.enqueue(new Callback<List<GetReview>>() {
            @Override
            public void onResponse(Call<List<GetReview>> call, Response<List<GetReview>> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG","response " + response.body().size());
                    reviewInfo.addAll(response.body());
                    getAllReviewAdapter.notifyDataSetChanged();
                    pbTask.setVisibility(View.GONE);
                    rvReviewView.setVisibility(View.VISIBLE);
                } else {
                    Log.d("TAG","response code " + response.code());
                    pbTask.setVisibility(View.GONE);
                    rvReviewView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<GetReview>> call, Throwable t) {
                Log.d("Tag","failure " + t);
                if(getContext()!=null)
                    Toast.makeText(getContext(),"Сервер не отвечает",Toast.LENGTH_LONG).show();
                pbTask.setVisibility(View.GONE);
                rvReviewView.setVisibility(View.VISIBLE);
            }
        });


        return rootView;
    }







    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.s_start:
                final SearchReview searchReview = new SearchReview();

                RatingBar rb=getActivity().findViewById(R.id.s_rate);
                TextView tvName=getActivity().findViewById(R.id.s_name);
                TextView tvDatetime=getActivity().findViewById(R.id.s_daterev);
                Spinner spinnerSpec=getActivity().findViewById(R.id.spinner);
                TextView tvCity=getActivity().findViewById(R.id.s_city);
                TextView tvAdress=getActivity().findViewById(R.id.s_street);

                if(chY.isChecked())
                    searchReview.setOnCall(1);
                else
                    searchReview.setOnCall(0);

                searchReview.setName(tvName.getText().toString());
                searchReview.setDatetime(tvDatetime.getText().toString());
                searchReview.setCity(tvCity.getText().toString());
                searchReview.setAdress(tvAdress.getText().toString());
                searchReview.setRate(rb.getRating());

                Class fragmentClass;
                fragmentClass = SearchReview_fragment.class;
                try {
                    Fragment myFragment = (Fragment) fragmentClass.newInstance();
                    myFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    final Handler handler = new Handler();

                    final Runnable r = new Runnable() {
                        public void run() {
                            dataFromActivityToFragment.sendSearchData(searchReview);
                        }
                    };
                    dataFromActivityToFragment = (DataSendFragment) myFragment;
                    fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();

                    handler.postDelayed(r, 0);
                }
                catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.checkBoxY:
                if(chY.isChecked())
                    chN.setChecked(false);
                break;
            case R.id.checkBoxN:
                if(chN.isChecked())
                    chY.setChecked(false);
                break;
        }
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
