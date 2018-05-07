package com.example.zz.zz;

import com.example.zz.zz.model.getAllReview.GetReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pavel on 02.05.2018.
 */

public interface ReviewGetter {
    @GET("getAllReviews")
    Call<List<GetReview>> serviceReview();
}
