package com.example.evitar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @POST("users/authenticate")
    Call<User> login(
            @Body SignIn body
    );
}
