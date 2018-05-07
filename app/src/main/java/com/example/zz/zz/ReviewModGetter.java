package com.example.zz.zz;

import com.example.zz.zz.model.getAllReview.GetReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pavel on 07.05.2018.
 */

public interface ReviewModGetter {
    @GET("getAllReviewsMod")
    Call<List<GetReview>> serviceReview();
}
