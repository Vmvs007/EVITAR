package com.example.evitar.Services;

import com.example.evitar.EpiFolder.EpiAlert;
import com.example.evitar.HomeFolder.LastMonths;
import com.example.evitar.LoginFolder.Colaborador;
import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.TipoEpiFolder.AddTipoEpi;
import com.example.evitar.TipoEpiFolder.TipoEpis;
import com.example.evitar.MovimentosFolder.Movimento;
import com.example.evitar.LoginFolder.SignIn;
import com.example.evitar.LoginFolder.User;
import com.example.evitar.DashFolder.Stats;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @POST("users/authenticate")
    Call<User> login(
            @Body SignIn body
    );

    @GET("api/Colaborador/{id}")
    Call<Colaborador> getColab(
            @Header("Authorization") String token,
            @Path("id") Long id
    );

    @GET("api/EPI/view")
    Call<List<Epi>> getEpis(
            @Header("Authorization") String token
    );

    @GET("api/TipoEPI")
    Call<List<TipoEpis>> getTipoEpis(
            @Header("Authorization") String token
    );

    @POST("api/TipoEPI")
    Call<TipoEpis> addTipoEpi(
            @Header("Authorization") String token,
            @Body AddTipoEpi tipoepi
    );

    @PUT("api/TipoEPI/{id}")
    Call<TipoEpis> editTipoEpi(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Body TipoEpis tipoepi
    );

    @GET("api/TipoEPI/{id}")
    Call<TipoEpis> getTipoEpi(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @POST("api/EPI")
    Call<Epi> addEpi(
            @Header("Authorization") String token,
            @Body Epi epi
    );

    @PUT("api/EPI/{id}")
    Call<Epi> editEpi(
            @Header("Authorization") String token,
            @Path("id") Long id,
            @Body Epi epi
    );


    @GET("api/Movimento/view")
    Call<List<Movimento>> getNotifications(
            @Header("Authorization") String token
    );

    @GET("api/Movimento/Entradas/{data}")
    Call<List<Movimento>> getMovimentosporDia(
            @Header("Authorization") String token,
            @Path("data") String data
    );

    @GET("api/Movimento/NumeroWarnigs/{data}")
    Call<LastMonths> getLastMonths(
            @Header("Authorization") String token,
            @Path("data") String data
    );

    @GET("api/Movimento/Stats/{data}")
    Call<Stats> getStats(
            @Header("Authorization") String token,
            @Path("data") String data
    );

    @GET("api/Movimento/alert/{data}")
    Call<List<Movimento>> getAllAlerts(
            @Header("Authorization") String token,
            @Path("data") String data
    );

    @GET("api/MovEpi/EpiWarningMov/{id}")
    Call<List<EpiAlert>> getAlerts(
            @Header("Authorization") String token,
            @Path("id") int id
    );

}
