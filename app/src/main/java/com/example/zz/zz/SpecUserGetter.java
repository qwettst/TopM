package com.example.zz.zz;

import com.example.zz.zz.model.getSpecUser.GetSpecUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pavel on 07.05.2018.
 */

public interface SpecUserGetter {
    @GET("getSpecUser/{id}")
    Call<GetSpecUser> serviceUser(@Path("id") int id);
}
