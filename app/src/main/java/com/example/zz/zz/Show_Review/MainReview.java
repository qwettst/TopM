package com.example.zz.zz.Show_Review;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zz.zz.DataSendFragment;
import com.example.zz.zz.R;
import com.example.zz.zz.database.DatabaseUserProfileHelper;
import com.example.zz.zz.model.AllReviewData;
import com.example.zz.zz.model.ReviewData;
import com.example.zz.zz.model.SearchReview;
import com.example.zz.zz.model.getAllReview.ReviewsParameter;

import java.util.ArrayList;
import java.util.List;


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
        if(ardMod!=null) {
            menu.clear();
            inflater.inflate(R.menu.toolbar_menu_review_settings, menu);
        }
        else
            menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

                case R.id.rev_edit:
                {
                    DatabaseUserProfileHelper db;
                    db=new DatabaseUserProfileHelper(getContext());
                        if(db.getUserById(5)!=null){

                            Class fragmentClass;
                            fragmentClass=edit_review.class;
                            Fragment myFragment=null;
                            FragmentManager mFragment=getActivity().getSupportFragmentManager();
                            try {

                                myFragment=(Fragment)fragmentClass.newInstance();

                            } catch (java.lang.InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            final Handler handler = new Handler();

                            final Runnable r = new Runnable() {
                                public void run() {
                                    dataSendFragment.sendData(ardMod);
                                }
                            };
                            dataSendFragment = (DataSendFragment) myFragment;

                            mFragment.beginTransaction().replace(R.id.flcontent,myFragment).commit();


                            handler.postDelayed(r, 0);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_rewiew, container,
                false);

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

    @Override
    public void sendReviewData(ReviewData data) {
        if(data != null) {
            tvAuthor.setText(data.getAuthor());
            tvName.setText(data.getNameMaster()+" ");
            tvSpec.setText(data.getSpec());
            tvCity.setText(data.getCity());
            tvStreet.setText(data.getStreet());
            tvDate.setText(data.getDateReview());
            tvReview.setText(data.gettReview());
            iPos=data.getPos();
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
