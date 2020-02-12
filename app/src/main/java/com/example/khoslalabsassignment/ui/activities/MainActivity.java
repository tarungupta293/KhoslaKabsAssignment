package com.example.khoslalabsassignment.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khoslalabsassignment.R;
import com.example.khoslalabsassignment.appUtils.AppConstants;
import com.example.khoslalabsassignment.appUtils.AppPref;
import com.example.khoslalabsassignment.appUtils.AppUtils;
import com.example.khoslalabsassignment.beans.WeatherApiiResponse;
import com.example.khoslalabsassignment.beans.WeatherList;
import com.example.khoslalabsassignment.ui.adapters.RecyclerAdapter;
import com.example.khoslalabsassignment.viewmodel.ProjectViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private boolean isMaximumDataDownloaded = false;
    private ArrayList<WeatherList> dataList = new ArrayList<>();
    private AppPref appPref;
    private ProjectViewModel projectViewModel;

    private LinearLayout layoutWeatherData;
    private TextView txtTemperature, txtCity;
    private RelativeLayout layoutError;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appPref = new AppPref(this);
        initialiseViews();

        getData();
    }

    private void getData() {
        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);

        observeViewModel(projectViewModel);
    }

    private void observeViewModel(ProjectViewModel viewModel) {
        // Update the list when the data changes
        changeLayoutVisibility(View.VISIBLE,View.GONE,View.GONE);

        if (viewModel.getProjectListObservable().hasObservers())
            viewModel.getProjectListObservable().removeObservers(this);

        viewModel.getProjectListObservable().observe(this, new Observer<WeatherApiiResponse>() {
            @Override
            public void onChanged(@Nullable WeatherApiiResponse weatherApiiResponse) {
                changeLayoutVisibility(View.GONE,View.VISIBLE,View.GONE);

                if (weatherApiiResponse != null && weatherApiiResponse.getList() != null && !weatherApiiResponse.getList().isEmpty()) {
                    String dateAddedInList = null;
                    for (int i = 0; i < weatherApiiResponse.getList().size(); i++) {
                        if (i == 0) {
//                            selectedWeatherList.add(weatherApiiResponse.getList().get(i));
                            showDataInUI(weatherApiiResponse.getList().get(i), weatherApiiResponse.getCity().getName());
//                            dateAddedInList = AppUtils.convertDateFormat(weatherApiiResponse.getList().get(i).getDtTxt(), AppConstants.DATE_FORMAT_WEATHER_API, AppConstants.DATE_FORMAT_SHOW_DATE_ONLY);
                        }
                        String convertedDateFormatStr = AppUtils.convertDateFormat(weatherApiiResponse.getList().get(i).getDtTxt(), AppConstants.DATE_FORMAT_WEATHER_API, AppConstants.DATE_FORMAT_SHOW_DATE_ONLY);
                        if (TextUtils.isEmpty(dateAddedInList) || !AppUtils.checkWhetherTwoDatesMatched(convertedDateFormatStr, dateAddedInList, AppConstants.DATE_FORMAT_SHOW_DATE_ONLY)) {
                            dataList.add(weatherApiiResponse.getList().get(i));
                            dateAddedInList = convertedDateFormatStr;
                        }
                    }
                    if (!dataList.isEmpty()){
                        RecyclerAdapter weatherAdapter = new RecyclerAdapter(MainActivity.this,dataList);
                        recyclerView.setAdapter(weatherAdapter);
                        Animation animation;
                        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_to_top_animation);
                        recyclerView.setAnimation(animation);
                    }else{
                        changeLayoutVisibility(View.GONE,View.GONE,View.VISIBLE);
                    }
                }else{
                    changeLayoutVisibility(View.GONE,View.GONE,View.VISIBLE);
                }

            }
        });
    }

    private void changeLayoutVisibility(int progressBarVisibility, int layoutDataVisibility, int layoutErrorVisibility) {
        progressBar.setVisibility(progressBarVisibility);
        layoutWeatherData.setVisibility(layoutDataVisibility);
        layoutError.setVisibility(layoutErrorVisibility);

    }

    private void initialiseViews() {
        progressBar = findViewById(R.id.progress_bar);
        layoutWeatherData = findViewById(R.id.layout_weather_data);
        txtTemperature = findViewById(R.id.txt_temp);
        txtCity = findViewById(R.id.txt_city);
        recyclerView = findViewById(R.id.recycler_weather_data);
        layoutError = findViewById(R.id.layout_error);
        btnRetry = findViewById(R.id.btnRetry);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        recyclerAdapter = new RecyclerAdapter(this,dataList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void showDataInUI(WeatherList weatherList,String cityName) {
        if (weatherList!=null && weatherList.getMain()!=null){
            txtTemperature.setText(AppUtils.convertKelvinToCelcius(weatherList.getMain().getTemp())+AppConstants.DEGREE_SYMBOL_UNICODE);
        }
        txtCity.setText(cityName);
    }
}
