package com.example.khoslalabsassignment.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.khoslalabsassignment.R;
import com.example.khoslalabsassignment.appUtils.AppConstants;
import com.example.khoslalabsassignment.appUtils.AppUtils;
import com.example.khoslalabsassignment.beans.WeatherList;

import java.util.List;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<WeatherList> dataList;

    public RecyclerAdapter(Context context, List<WeatherList> list){
        this.context = context;
        this.dataList = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (dataList.get(position).getMain()!=null){
            holder.txtTemperature.setText(AppUtils.convertFahrenheitToCelcius(dataList.get(position).getMain().getTemp())+ AppConstants.DEGREE_SYMBOL_UNICODE+"C");
        }
        if (!TextUtils.isEmpty(dataList.get(position).getDtTxt())){
            holder.txtDay.setText(AppUtils.getDayOfWeekFromDate(dataList.get(position).getDtTxt(),AppConstants.DATE_FORMAT_WEATHER_API));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateAdapter(List<WeatherList> list){
        this.dataList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        private TextView txtDay,txtTemperature;
        private RelativeLayout layoutWeather;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txt_day);
            txtTemperature = itemView.findViewById(R.id.txt_temperature);
//            layoutWeather = itemView.findViewById(R.id.layout_weather);
        }
    }
}
