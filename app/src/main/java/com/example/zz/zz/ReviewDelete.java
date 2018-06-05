package com.example.zz.zz;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Pavel on 05.06.2018.
 */

public interface ReviewDelete {
    @POST("deleteReview")
    Call<Void> deleteReview(@Query("idReview") String str);
}
