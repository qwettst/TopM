package com.example.zz.zz.Show_Review;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.zz.zz.DataSendFragment;
import com.example.zz.zz.R;
import com.example.zz.zz.model.AllReviewData;
import com.example.zz.zz.model.ReviewData;
import com.example.zz.zz.database.DatabaseReviewHelper;
import com.example.zz.zz.model.Review;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link edit_review.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link edit_review#newInstance} factory method to
 * create an instance of this fragment.
 */
public class edit_review extends Fragment implements DataSendFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public edit_review() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment edit_review.
     */
    // TODO: Rename and change types and number of parameters
    public static edit_review newInstance(String param1, String param2) {
        edit_review fragment = new edit_review();
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
                String name, spec, city, street, rev,author;
                name=(etFname.getText().toString()+" "+etLname.getText().toString());
                spec=etSpec.getText().toString();
                city=etCity.getText().toString();
                street=etStreet.getText().toString();
                rev=etRev.getText().toString();
                DateFormat df = new SimpleDateFormat("dd.MM.yy");
                String date = df.format(Calendar.getInstance().getTime());
                author="admin";
                float rate;
                rate=(rbQuality.getRating()+ rbComnctn.getRating()+ rbKonflict.getRating())/3;

                DatabaseReviewHelper db=new DatabaseReviewHelper(getActivity());

                Review review=new Review(0,author,name,spec,city,street,date,rev,rate);
                db.updateReview(review,id);
                Class fragmentClass;
                fragmentClass=myReview.class;
                try {
                    Fragment myFragment=(Fragment)fragmentClass.newInstance();
                    FragmentManager fragmentManager = getFragmentManager();

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

    private RatingBar rbQuality, rbComnctn, rbKonflict;
    private EditText etFname, etLname, etSpec, etCity, etStreet, etRev;
    private int id;

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
        View rootView = inflater.inflate(R.layout.fragment_edit_review, container,
                false);

        ImageView ivBackground;

        ivBackground=(ImageView) rootView.findViewById(R.id.background_image);
        ivBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(this)
                .load(R.drawable.background)
                .into(ivBackground);

        getActivity().findViewById(R.id.search_form).setVisibility(View.GONE);
        rbQuality = (RatingBar) rootView.findViewById(R.id.ratingBar);
        rbComnctn = (RatingBar) rootView.findViewById(R.id.ratingBar2);
        rbKonflict = (RatingBar) rootView.findViewById(R.id.ratingBar3);

        etFname=(EditText)rootView.findViewById(R.id.send_first_name);
        etLname=(EditText)rootView.findViewById(R.id.send_last_name);
        etSpec=(EditText)rootView.findViewById(R.id.send_spec);
        etCity=(EditText)rootView.findViewById(R.id.send_city);
        etStreet=(EditText)rootView.findViewById(R.id.send_street);
        etRev=(EditText)rootView.findViewById(R.id.send_infoRev);

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


    @Override
    public void sendData(AllReviewData data) {

    }

    @Override
    public void sendReviewData(ReviewData data) {
        etFname.setText(data.getNameMaster());
        etSpec.setText(data.getSpec());
        etCity.setText(data.getCity());
        etStreet.setText(data.getStreet());
        etRev.setText(data.gettReview());
        id=data.getPos();
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