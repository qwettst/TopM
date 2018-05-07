package com.example.zz.zz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zz.zz.R;
import com.example.zz.zz.LK_User.reviewInfo_lk;

import java.util.List;


/**
 * Created by Pavel on 08.04.2018.
 */

public class ReviewInfo_LK_Adapter extends RecyclerView.Adapter<ReviewInfo_LK_Adapter.MyViewHolder> {

    private List<reviewInfo_lk> reviewInfo_lk_List;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView author,review,date;

        public MyViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.inforev_author);
            review = (TextView) view.findViewById(R.id.inforev_review);
            date = (TextView) view.findViewById(R.id.inforev_date);

        }
    }

    public ReviewInfo_LK_Adapter(List<reviewInfo_lk> reviewInfo_lk_List) {
        this.reviewInfo_lk_List = reviewInfo_lk_List;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lk_review_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        reviewInfo_lk reviewInfo_lk = reviewInfo_lk_List.get(position);
        holder.author.setText(reviewInfo_lk.getAuthor());
        holder.review.setText(reviewInfo_lk.getShortReview());
        holder.date.setText(reviewInfo_lk.getDate());
    }

    @Override
    public int getItemCount() {
        return reviewInfo_lk_List.size();
    }

}
