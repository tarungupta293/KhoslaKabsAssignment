package com.example.khoslalabsassignment.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.khoslalabsassignment.beans.WeatherApiiResponse;
import com.example.khoslalabsassignment.beans.WeatherList;
import com.example.khoslalabsassignment.repository.ApiRepository;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {
    private LiveData<WeatherApiiResponse> projectListObservable;

    public ProjectViewModel(Application application) {
        super(application);

        getWeatherData();
    }

    public void getWeatherData() {
        projectListObservable = ApiRepository.getInstance().getProjectList();
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<WeatherApiiResponse> getProjectListObservable() {
        return projectListObservable;
    }
}
