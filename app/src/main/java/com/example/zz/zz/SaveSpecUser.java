package com.example.zz.zz;



import com.example.zz.zz.model.saveSpecUser.SpecUser;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Pavel on 02.05.2018.
 */

public interface SaveSpecUser {
    @POST("saveSpecUser")
    Call<SpecUser> saveSpecUser(@Query("SpecUser") String str);
}
