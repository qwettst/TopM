package com.example.zz.zz.database;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.zz.zz.DataSendFragment;
import com.example.zz.zz.R;
import com.example.zz.zz.Show_Review.MainReview;
import com.example.zz.zz.model.ReviewData;
import com.example.zz.zz.model.Review;

import java.util.List;

/**
 * Created by Pavel on 08.04.2018.
 */

public class Review_DB_Adapter extends RecyclerView.Adapter<Review_DB_Adapter.MyViewHolder> {

    private Context context;
    private List<Review> reviewList;
    private FragmentManager mFragment;
    private DataSendFragment dataSendFragment;

    public Review_DB_Adapter(Context context, List<Review> reviewList, FragmentManager fragment) {
        this.context = context;
        this.reviewList = reviewList;
        mFragment  = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameMaster, spec ,city,street,date;
        public RatingBar rate;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            nameMaster = (TextView) view.findViewById(R.id.employee_first_name);
            spec= (TextView) view.findViewById(R.id.employee_spec);
            city = (TextView) view.findViewById(R.id.employee_city);
            street = (TextView) view.findViewById(R.id.employee_street);
            date = (TextView) view.findViewById(R.id.date);
            rate=(RatingBar)view.findViewById(R.id.ratingBar);
        }
        @Override
        public void onClick(View view) {

            int position = getLayoutPosition();
            Review review = reviewList.get(position);
            final ReviewData reviewData=new ReviewData();

            reviewData.setAuthor(review.getAuthor());
            reviewData.setNameMaster(review.getNameMaster());
            reviewData.setSpec(review.getSpec());
            reviewData.setCity(review.getCity());
            reviewData.setStreet(review.getStreet());
            reviewData.setDateReview(review.getDateReview());
            reviewData.setTReview(review.gettReview());
            reviewData.setPos(review.getIdR());

            Class fragmentClass;
            fragmentClass= MainReview.class;
            Fragment myFragment=null;
//
//            try {
//                myFragment=(Fragment)fragmentClass.newInstance();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            final Handler handler = new Handler();
//
//            final Runnable r = new Runnable() {
//                public void run() {
//                    dataSendFragment.sendReviewData(reviewData);
//                }
//            };
//            dataSendFragment = (DataSendFragment) myFragment;
//
//            mFragment.beginTransaction().replace(R.id.flcontent,myFragment).commit();
//
//
//            handler.postDelayed(r, 0);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.nameMaster.setText(review.getNameMaster());
        holder.spec.setText(review.getSpec());
        holder.city.setText(review.getCity());
        holder.street.setText(review.getStreet());
        holder.date.setText(review.getDateReview());
        holder.rate.setRating(review.getRevRate());
    }
    @Override
    public int getItemCount() {
        return reviewList.size();
    }


    public Review getReviewByPos(int position){
        Review review=reviewList.get(position);
        return review;
    }

}

