package com.example.zz.zz;

import com.example.zz.zz.model.getAllReview.GetReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pavel on 16.05.2018.
 */

public interface UserReviewGetter {
    @GET("getUserReviews/{id}")
    Call<List<GetReview>> serviceReview(@Path("id") int id);
}
