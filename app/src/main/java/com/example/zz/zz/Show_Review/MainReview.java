package com.example.zz.zz.Show_Review;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zz.zz.AddReviewToLK;
import com.example.zz.zz.BuildConfig;
import com.example.zz.zz.CheckModGetter;
import com.example.zz.zz.DataSendFragment;
import com.example.zz.zz.LK_User.LK;
import com.example.zz.zz.LoginUser;
import com.example.zz.zz.R;
import com.example.zz.zz.ReviewDelete;
import com.example.zz.zz.model.AddSpecUserReview;
import com.example.zz.zz.model.AllReviewData;
import com.example.zz.zz.model.CheckMod;
import com.example.zz.zz.model.SearchReview;
import com.example.zz.zz.model.getAllReview.ReviewsParameter;
import com.example.zz.zz.model.getAllReview.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainReview extends Fragment implements DataSendFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    private OnFragmentInteractionListener mListener;

    public MainReview() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainRewiew.
     */
    // TODO: Rename and change types and number of parameters
    public static MainReview newInstance(String param1, String param2) {
        MainReview fragment = new MainReview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if(ardMod!=null) {
            if (ardMod.getStatus() == 0 && bundle.getInt("UserReviewL")!=1)
                inflater.inflate(R.menu.toolbar_menu_review_settings, menu);
            else {
                if (ardMod.getStatus() == 0 && bundle.getInt("UserReviewL")==1)
                    inflater.inflate(R.menu.toolbar_menu_review_settings_for_user, menu);
                else
                    if (bundle.getInt("uID") != -1) {
                        if (ardMod.getIdSpecUser() != null)
                            inflater.inflate(R.menu.toolbar_menu_review_user_withspec, menu);
                        else
                            inflater.inflate(R.menu.toolbar_menu_review_user_nonspec, menu);
                    }
            }
        }
        else
            if(bundle.getInt("uID")!=-1)
                inflater.inflate(R.menu.toolbar_menu_review_user_nonspec, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if(BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY );
        }

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        switch (item.getItemId()) {

            case R.id.rev_del:
            {
                Retrofit rfti = new Retrofit.Builder()
                        .baseUrl("http://94.251.14.36:8080/TopMaster/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okClient)
                        .build();
                ReviewDelete reviewDelete=rfti.create(ReviewDelete.class);

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = null;
                try {
                    jsonString = mapper.writeValueAsString(ardMod.getIdReview());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                System.out.println("json " + jsonString);

                final Call<Void> call = reviewDelete.deleteReview(jsonString);


                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

                        } else {
                            Log.d("TAG","response code " + response.code());

                        }
                        if (response.code()==200) {
                            Toasty.success(getContext(), "Отзыв удален", Toast.LENGTH_LONG).show();
                            openModReview();
                        }

                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Tag","failure " + t);

                        Toasty.error(getContext(),"Сервер не отвечает",Toast.LENGTH_LONG).show();
                    }
                });


                break;
            }

            case R.id.rev_edit:
                {
                    Class fragmentClass;
                    fragmentClass=edit_review.class;
                    Fragment myFragment=null;
                    FragmentManager mFragment=getActivity().getSupportFragmentManager();
                    try {
                        myFragment=(Fragment)fragmentClass.newInstance();
                        myFragment.setArguments(bundle);
                        final Handler handler = new Handler();

                        final Runnable r = new Runnable() {
                                    public void run() {dataSendFragment.sendData(ardMod);
                                    }
                                };
                                dataSendFragment = (DataSendFragment) myFragment;

                                mFragment.beginTransaction().replace(R.id.flcontent,myFragment).commit();


                        handler.postDelayed(r, 0);

                    } catch (java.lang.InstantiationException e) {
                                e.printStackTrace();
                    } catch (IllegalAccessException e) {
                                e.printStackTrace();
                    }
                    break;
                }

            case R.id.rev_addReview:
            {
                Retrofit rfti = new Retrofit.Builder()
                        .baseUrl("http://94.251.14.36:8080/TopMaster/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okClient)
                        .build();

                AddSpecUserReview addR=new AddSpecUserReview(bundle.getInt("uID"),ardMod.getIdReview());

                AddReviewToLK addReviewToLK = rfti.create(AddReviewToLK.class);


                ObjectMapper mapper = new ObjectMapper();
                String jsonString = null;
                try {
                    jsonString = mapper.writeValueAsString(addR);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                System.out.println("json " + jsonString);



                final Call<Void> call = addReviewToLK.addSpecReview(jsonString);


                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

                        } else {
                            Log.d("TAG","response code " + response.code());

                        }
                        if (response.code()==200)
                            Toasty.success(getContext(),"Отзыв добавлен в ЛК",Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Tag","failure " + t);
                        Toasty.error(getContext(),"Сервер не отвечает",Toast.LENGTH_LONG).show();
                    }
                });
                break;
            }
            case R.id.rev_showLK:{
                bundle.putInt("uIDSpec",ardMod.getIdSpecUser());
                Class fragmentClass;
                fragmentClass=LK.class;
                Fragment myFragment=null;
                FragmentManager mFragment=getActivity().getSupportFragmentManager();
                try {
                    myFragment=(Fragment)fragmentClass.newInstance();
                    myFragment.setArguments(bundle);
                    mFragment.beginTransaction().replace(R.id.flcontent,myFragment).commit();

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView tvName;
    private TextView tvSpec;
    private TextView tvCity;
    private TextView tvStreet;
    private TextView tvReview;
    private TextView tvDate;
    private TextView tvAuthor;
    private RatingBar rb1, rb2, rb3;
    private int iPos=-1;
    private DataSendFragment dataSendFragment;
    private AllReviewData ardMod;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_rewiew, container,
                false);

        bundle = this.getArguments();


        ImageView ivBackground;

        ivBackground=(ImageView) rootView.findViewById(R.id.background_image);
        ivBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(this)
                .load(R.drawable.background)
                .into(ivBackground);


        getActivity().findViewById(R.id.search_form).setVisibility(View.GONE);
        tvName=(TextView)rootView.findViewById(R.id.revUname);
        tvSpec=(TextView)rootView.findViewById(R.id.revUSpec);
        tvCity=(TextView)rootView.findViewById(R.id.revUCity);
        tvStreet=(TextView)rootView.findViewById(R.id.revUStreet);
        tvReview=(TextView)rootView.findViewById(R.id.revInfo);
        tvDate=(TextView)rootView.findViewById(R.id.revDate);
        tvAuthor=(TextView)rootView.findViewById(R.id.revAuthor);
        rb1=(RatingBar)rootView.findViewById(R.id.ratingBar1);
        rb2=(RatingBar)rootView.findViewById(R.id.ratingBar2);
        rb3=(RatingBar)rootView.findViewById(R.id.ratingBar3);

        rb1.setRating(0);
        rb2.setRating(0);
        rb3.setRating(0);

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


    public void sendData(AllReviewData data) {
        if(data != null) {
            ardMod=data;
            tvAuthor.setText(data.getUser().getName()+" "+data.getUser().getSurname());
            tvName.setText(data.getName()+" "+data.getSurname()+" "+data.getOtchestvo());
            tvCity.setText(data.getCity());
            tvStreet.setText(data.getAddress());
            tvReview.setText(data.getContent());
            tvDate.setText(data.getDatetime());
            tvSpec.setText(data.getSpec());


            List<ReviewsParameter> reviewsParameterList=new ArrayList<>();
            reviewsParameterList.addAll(data.getReviewsParameters());
            if( reviewsParameterList.size()!=0 ) {
                rb1.setRating(reviewsParameterList.get(0).getValue());
                rb2.setRating(reviewsParameterList.get(1).getValue());
                rb3.setRating(reviewsParameterList.get(2).getValue());
            }
        }
    }

    private void openModReview(){
        Bundle bSReview=new Bundle();
        bSReview.putString("mSign","mSign");
        bSReview.putInt("uID", bundle.getInt("uID"));

        Class fragmentClass;
        fragmentClass=allReview.class;
        try {
            Fragment myFragment=(Fragment)fragmentClass.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            myFragment.setArguments(bSReview);
            View view = getActivity().getCurrentFocus();
            fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void sendSearchData(SearchReview data) {

    }


    @Override
    public void onClick(View view) {

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
