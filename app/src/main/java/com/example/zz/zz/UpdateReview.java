package com.example.zz.zz;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Pavel on 06.06.2018.
 */

public interface UpdateReview {
    @POST("updateReview")
    Call<Void> updateReview(@Query("Review") String str);
}
