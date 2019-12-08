package com.example.evitar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @POST("users/authenticate")
    Call<User> login(
            @Body SignIn body
    );

    @GET("api/EPI")
    Call<List<Epi>> getEpis();
}
