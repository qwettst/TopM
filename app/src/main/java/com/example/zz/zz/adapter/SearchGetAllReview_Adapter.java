package com.example.zz.zz.adapter;

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
import com.example.zz.zz.model.AllReviewData;
import com.example.zz.zz.model.SearchReview;
import com.example.zz.zz.model.getAllReview.GetReview;
import com.example.zz.zz.model.getAllReview.ReviewsParameter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 12.05.2018.
 */

public class SearchGetAllReview_Adapter  extends RecyclerView.Adapter<SearchGetAllReview_Adapter.MyViewHolder>{
    private List<GetReview> getReviewList;
    private FragmentManager mFragment;
    private DataSendFragment dataFromActivityToFragment;
    private SearchReview searchReview;
    private int i_posremove =0;

    public SearchGetAllReview_Adapter(List<GetReview> getReviewList,SearchReview searchReview, FragmentManager fragment) {
        this.getReviewList = getReviewList;
        mFragment  = fragment;
        this.searchReview=searchReview;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView first_name, spec ,city,street,date;
        public RatingBar rate;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            first_name = (TextView) view.findViewById(R.id.employee_first_name);
            spec= (TextView) view.findViewById(R.id.employee_spec);
            city = (TextView) view.findViewById(R.id.employee_city);
            street = (TextView) view.findViewById(R.id.employee_street);
            date = (TextView) view.findViewById(R.id.date);
            rate= (RatingBar) view.findViewById(R.id.ratingBar);
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

            Class fragmentClass;
            fragmentClass=MainReview.class;
            Fragment myFragment=null;

            try {
                myFragment=(Fragment)fragmentClass.newInstance();
                final Handler handler = new Handler();

                final Runnable r = new Runnable() {
                    public void run() {
                        dataFromActivityToFragment.sendData(allReviewData);
                    }
                };
                dataFromActivityToFragment = (DataSendFragment) myFragment;
                mFragment.beginTransaction().replace(R.id.flcontent,myFragment).commit();


                handler.postDelayed(r, 10);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }



        }

    }

    @Override
    public SearchGetAllReview_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new SearchGetAllReview_Adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        int fl=0;

        GetReview getReview = getReviewList.get(position);

        float rateReview=0;
        List<ReviewsParameter> reviewsParameterList=new ArrayList<>();
        reviewsParameterList.addAll(getReview.getReviewsParameters());
        if(reviewsParameterList.size()!=0)
            rateReview=(reviewsParameterList.get(0).getValue()+reviewsParameterList.get(1).getValue()+reviewsParameterList.get(2).getValue())/3;

        String s_name=getReview.getName() + " " + getReview.getSurname() + " " + getReview.getOtchestvo();

        holder.first_name.setText(s_name);
        holder.spec.setText(getReview.getSpecName());
        holder.city.setText(getReview.getCity());
        holder.street.setText(getReview.getAddress());
        holder.date.setText(getReview.getDatetime());
        holder.rate.setRating(rateReview);
        if(s_name.contains(searchReview.getName())) {
            if (getReview.getCity().contains(searchReview.getCity())) {
                if (getReview.getAddress().contains(searchReview.getAdress()));
                else {
                    removeItem(position-i_posremove);
                    fl=1;
                    i_posremove++;
                }
            }
            else {
                removeItem(position-i_posremove);
                fl=1;
                i_posremove++;
            }
        }
        else {
            removeItem(position-i_posremove);
            fl=1;
            i_posremove++;
        }

        if(rateReview < searchReview.getRate() && fl!=1) {
            removeItem(position - i_posremove);
            i_posremove++;
        }
    }



    public void removeItem(final int position) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(position>=0) {
                    getReviewList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getReviewList.size();
    }

}
