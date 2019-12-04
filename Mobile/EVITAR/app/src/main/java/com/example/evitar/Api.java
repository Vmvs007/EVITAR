package com.example.evitar;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("users/authenticate")
    Call<User> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
