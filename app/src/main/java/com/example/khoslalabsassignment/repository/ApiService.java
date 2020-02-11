package com.example.khoslalabsassignment.repository;

import com.example.khoslalabsassignment.beans.WeatherApiiResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("forecast")
    Call<WeatherApiiResponse> getWeatherResults(@Query("q") String city, @Query("APPID") String appId);
}
