package com.example.zz.zz;



import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Pavel on 23.05.2018.
 */

public interface AddReviewToLK {
    @POST("addSpecReview")
    Call<Void> addSpecReview(@Query("SpecReview") String str);
}
