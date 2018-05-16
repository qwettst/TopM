package com.example.zz.zz.Show_Review;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.zz.zz.BuildConfig;
import com.example.zz.zz.R;
import com.example.zz.zz.SaveReviewToServer;
import com.example.zz.zz.model.getAllReview.ReviewsParameter;
import com.example.zz.zz.model.getAllReview.User;
import com.example.zz.zz.model.saveReview.ParametrRate;
import com.example.zz.zz.model.saveReview.SaveReview;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendReview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendReview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendReview extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SendReview() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendReview.
     */
    // TODO: Rename and change types and number of parameters
    public static SendReview newInstance(String param1, String param2) {
        SendReview fragment = new SendReview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_menu_send, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_send: {
                SaveReview svReview=new SaveReview();
                if(etRev.getText().toString().length()!=0)
                {
                    User u=new User();
                    u.setIdUser(iUID);

                    svReview.setUser(u);
                    svReview.setName(etName.getText().toString());
                    svReview.setSurname(etFname.getText().toString());
                    svReview.setOtchestvo( etOtch.getText().toString());
                    svReview.setCity(etCity.getText().toString());
                    svReview.setAddress(etStreet.getText().toString());
                    svReview.setContent(etRev.getText().toString());
                    svReview.setSpecName(etSpec.getText().toString());

                    if(chOnCallYes.isChecked())
                        svReview.setOnCall(1);
                    else
                        svReview.setOnCall(0);

                    List<ParametrRate> rateReviewList=new ArrayList<>();
                    ParametrRate rvParam=new ParametrRate();
                    rvParam.setValue(rbQuality.getRating());
                    rvParam.setParameterName("Качество услуг");
                    rateReviewList.add(rvParam);

                    rvParam=new ParametrRate();
                    rvParam.setValue( rbCommunctn.getRating());
                    rvParam.setParameterName("Коммуникация");
                    rateReviewList.add(rvParam);

                    rvParam=new ParametrRate();
                    rvParam.setValue( rbKonflict.getRating());
                    rvParam.setParameterName("Конфликтность");
                    rateReviewList.add(rvParam);

                    svReview.setReviewsParameters(rateReviewList);


                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

                    if(BuildConfig.DEBUG){
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY );
                    }

                    OkHttpClient okClient = new OkHttpClient.Builder()
                            .addInterceptor(loggingInterceptor)
                            .build();

                    Retrofit rftiReview = new Retrofit.Builder()
                            .baseUrl("http://94.251.14.36:8080/TopMaster/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okClient)
                            .build();

                    SaveReviewToServer reviewToServer = rftiReview.create(SaveReviewToServer.class);


                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = null;
                    try {
                        jsonString = mapper.writeValueAsString(svReview);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    System.out.println("json " + jsonString);



                    final Call<SaveReview> call = reviewToServer.saveReview(jsonString);


                    call.enqueue(new Callback<SaveReview>() {
                        @Override
                        public void onResponse(Call<SaveReview> call, Response<SaveReview> response) {
                            if (response.isSuccessful()) {
                                Log.d("TAG", "response " + response.body());
                            } else {
                                Log.d("TAG", "response code " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<SaveReview> call, Throwable t) {
                            Log.d("Tag","failure " + t);
                        }
                    });

                }
                Bundle bSReview=new Bundle();
                bSReview.putString("mSign",bundle.getString("mSign"));
                bSReview.putInt("uID", bundle.getInt("uID"));

                Class fragmentClass;
                fragmentClass=allReview.class;
                try {
                    Fragment myFragment=(Fragment)fragmentClass.newInstance();
                    FragmentManager fragmentManager = getFragmentManager();
                    myFragment.setArguments(bSReview);
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private RatingBar rbQuality, rbCommunctn, rbKonflict;
    private EditText etName, etFname,etOtch, etSpec, etCity, etStreet, etRev;
    private CheckBox chOnCallYes,chOnCallNo;
    private int iUID;
    private Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_send_review, container,
                false);


        bundle = this.getArguments();

        iUID= bundle.getInt("uID");
        ImageView ivBackground;

        ivBackground=(ImageView) rootView.findViewById(R.id.background_image);
        ivBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(this)
                .load(R.drawable.background)
                .into(ivBackground);

        getActivity().findViewById(R.id.search_form).setVisibility(View.GONE);
        rbQuality = (RatingBar) rootView.findViewById(R.id.ratingBar);
        rbCommunctn = (RatingBar) rootView.findViewById(R.id.ratingBar2);
        rbKonflict = (RatingBar) rootView.findViewById(R.id.ratingBar3);

        etName=(EditText)rootView.findViewById(R.id.send_name);
        etFname=(EditText)rootView.findViewById(R.id.send_first_name);
        etOtch=(EditText)rootView.findViewById(R.id.send_otchestvo);
        etSpec=(EditText)rootView.findViewById(R.id.send_spec);
        etCity=(EditText)rootView.findViewById(R.id.send_city);
        etStreet=(EditText)rootView.findViewById(R.id.send_street);
        etRev=(EditText)rootView.findViewById(R.id.send_infoRev);

        chOnCallYes=(CheckBox)rootView.findViewById(R.id.onCallYes);
        chOnCallNo=(CheckBox)rootView.findViewById(R.id.onCallNo);

        chOnCallYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chOnCallYes.isChecked())
                    chOnCallNo.setChecked(false);
            }
        });

        chOnCallNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chOnCallNo.isChecked())
                    chOnCallYes.setChecked(false);
            }
        });

        getActivity().setTitle("Написать отзыв");
        rbQuality.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
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
