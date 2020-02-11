package com.example.khoslalabsassignment.repository;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.khoslalabsassignment.R;
import com.example.khoslalabsassignment.appUtils.AppConstants;
import com.example.khoslalabsassignment.beans.WeatherApiiResponse;
import com.example.khoslalabsassignment.beans.WeatherList;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private static ApiRepository apiRepository;

    public synchronized static ApiRepository getInstance() {
        if (apiRepository == null) {
            if (apiRepository == null) {
                apiRepository = new ApiRepository();
            }
        }
        return apiRepository;
    }

    public LiveData<WeatherApiiResponse> getProjectList() {
        final MutableLiveData<WeatherApiiResponse> data = new MutableLiveData<>();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<WeatherApiiResponse> call = apiService.getWeatherResults(AppConstants.WEATHER_API_VALUE_BANGALORE,"f1a0f33ba4677f7661492274a6ea86be");
        call.enqueue(new Callback<WeatherApiiResponse>() {
            @Override
            public void onResponse(Call<WeatherApiiResponse> call, Response<WeatherApiiResponse> response) {
                try {
                    WeatherApiiResponse weatherApiiResponse = response.body();
                    if (weatherApiiResponse != null) {
                        data.setValue(weatherApiiResponse);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WeatherApiiResponse> call, Throwable t) {
                Log.e("errorbody",t.getMessage());
                data.setValue(null);
            }
        });

        return data;
    }
}
