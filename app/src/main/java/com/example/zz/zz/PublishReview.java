package com.example.zz.zz;

import com.example.zz.zz.model.saveReview.SaveModReview;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Pavel on 11.05.2018.
 */

public interface PublishReview {
    @POST("publishReview")
    Call<Void> publishreview(@Query("Review") String str);
}
