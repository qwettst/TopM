package com.example.zz.zz;

import com.example.zz.zz.model.saveReview.SaveReview;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Pavel on 07.05.2018.
 */

public interface SaveReviewToServer {
    @POST("saveReview")
    Call<SaveReview> saveReview(@Query("Review") String str);
}
