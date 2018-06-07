package com.example.zz.zz;

import com.example.zz.zz.model.CheckMod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pavel on 06.06.2018.
 */

public interface CheckModGetter {
    @GET("checkMod/{id}")
    Call<CheckMod> checkMod(@Path("id") int id);
}
