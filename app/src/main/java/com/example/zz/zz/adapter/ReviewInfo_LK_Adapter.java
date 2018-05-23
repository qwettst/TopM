package com.example.zz.zz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zz.zz.R;
import com.example.zz.zz.model.getAllReview.GetReview;
import com.example.zz.zz.model.getAllReview.ReviewsParameter;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Pavel on 08.04.2018.
 */

public class ReviewInfo_LK_Adapter extends RecyclerView.Adapter<ReviewInfo_LK_Adapter.MyViewHolder> {

    private List<GetReview> getReviewList;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView author,review,date;

        public MyViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.inforev_author);
            review = (TextView) view.findViewById(R.id.inforev_review);
            date = (TextView) view.findViewById(R.id.inforev_date);

        }
    }

    public ReviewInfo_LK_Adapter(List<GetReview> getReviewList) {
        this.getReviewList = getReviewList;
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
