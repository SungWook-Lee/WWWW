package com.example.ww;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=UTF-8",
            "appKey: eae051bd-f7a1-48d1-b093-f48f0c834af0"
    })
    @GET("current/minutely")
    Call<RetrofitClient> currentweather(@Query("version") String version, @Query("lat") String lat, @Query("lon") String lon);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=UTF-8",
            "appKey: eae051bd-f7a1-48d1-b093-f48f0c834af0"
    })
    @GET("forecast/3days")
    Call<RetrofitClient> shortWeather(@Query("version") String version, @Query("lat") String lat, @Query("lon") String lon);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=UTF-8",
            "appKey: eae051bd-f7a1-48d1-b093-f48f0c834af0"
    })
    @GET("forecast/6days")
    Call<RetrofitClient> longWeather(@Query("version") String version, @Query("lat") String lat, @Query("lon") String lon);
}