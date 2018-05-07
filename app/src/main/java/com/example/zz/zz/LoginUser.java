package com.example.zz.zz;

import com.example.zz.zz.model.getAllReview.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Pavel on 02.05.2018.
 */

public interface LoginUser {
    @POST("login")
    Call<User> createUser(@Query("User") String str);
}
