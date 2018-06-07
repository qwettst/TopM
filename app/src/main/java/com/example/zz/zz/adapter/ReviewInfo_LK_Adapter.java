package com.example.zz.zz.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zz.zz.DataSendFragment;
import com.example.zz.zz.R;
import com.example.zz.zz.Show_Review.MainReview;
import com.example.zz.zz.model.AllReviewData;
import com.example.zz.zz.model.getAllReview.GetReview;
import com.example.zz.zz.model.getAllReview.ReviewsParameter;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Pavel on 08.04.2018.
 */

public class ReviewInfo_LK_Adapter extends RecyclerView.Adapter<ReviewInfo_LK_Adapter.MyViewHolder> {

    private List<GetReview> getReviewList;
    private DataSendFragment dataFromActivityToFragment;
    private FragmentManager mFragment;
    Bundle bundle;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView author,review,date;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            author = (TextView) view.findViewById(R.id.inforev_author);
            review = (TextView) view.findViewById(R.id.inforev_review);
            date = (TextView) view.findViewById(R.id.inforev_date);

        }
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            GetReview getReview = getReviewList.get(position);
            final AllReviewData allReviewData = new AllReviewData();
            allReviewData.setIdReview(getReview.getIdReview());
            allReviewData.setUser(getReview.getUser());
            allReviewData.setSurname(getReview.getSurname());
            allReviewData.setName(getReview.getName());
            allReviewData.setOtchestvo(getReview.getOtchestvo());
            allReviewData.setCity(getReview.getCity());
            allReviewData.setAddress(getReview.getAddress());
            allReviewData.setDatetime(getReview.getDatetime());
            allReviewData.setContent(getReview.getContent());
            allReviewData.setSpec(getReview.getSpecName());
            allReviewData.setReviewsParameters(getReview.getReviewsParameters());
            allReviewData.setStatus(getReview.getStatus());
            allReviewData.setIdSpecUser(getReview.getIdSpecUser());

            Class fragmentClass;
            fragmentClass=MainReview.class;
            Fragment myFragment=null;

            try {
                myFragment=(Fragment)fragmentClass.newInstance();
                myFragment.setArguments(bundle);
                final Handler handler = new Handler();

                final Runnable r = new Runnable() {
                    public void run() {
                        dataFromActivityToFragment.sendData(allReviewData);
                    }
                };
                dataFromActivityToFragment = (DataSendFragment) myFragment;
                mFragment.beginTransaction().replace(R.id.flcontent,myFragment).commit();


                handler.postDelayed(r, 0);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }



        }
    }

    public ReviewInfo_LK_Adapter(List<GetReview> getReviewList, FragmentManager fragment, Bundle bundle) {
        this.getReviewList = getReviewList;
        mFragment  = fragment;
        this.bundle=bundle;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lk_review_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetReview getReview = getReviewList.get(position);
        holder.author.setText(getReview.getUser().getSurname()+" "+getReview.getUser().getName());
        holder.review.setText(getReview.getContent());
        holder.date.setText(getReview.getDatetime());
    }

    @Override
    public int getItemCount() {
        return getReviewList.size();
    }

    public float getRateSpec() {
        float frate=0;
        int i=getItemCount();
        GetReview getReview = null;
        for (int k=0;k<i;k++) {
            getReview = getReviewList.get(k);
            List<ReviewsParameter> reviewsParameterList = new ArrayList<>();
            reviewsParameterList.addAll(getReview.getReviewsParameters());
            if (reviewsParameterList.size() != 0)
                frate = (reviewsParameterList.get(0).getValue() + reviewsParameterList.get(1).getValue() + reviewsParameterList.get(2).getValue()) / 3 + frate;
        }

        return frate/getItemCount();
    }


}
