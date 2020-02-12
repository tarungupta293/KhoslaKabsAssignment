package com.example.khoslalabsassignment.ui.adapters;

import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khoslalabsassignment.R;
import com.example.khoslalabsassignment.appUtils.AppConstants;
import com.example.khoslalabsassignment.appUtils.AppUtils;
import com.example.khoslalabsassignment.beans.WeatherList;

import java.util.Calendar;
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
            holder.txtTemperature.setText(AppUtils.convertKelvinToCelcius(dataList.get(position).getMain().getTempMin())+ AppConstants.DEGREE_SYMBOL_UNICODE + "/" + AppUtils.convertKelvinToCelcius(dataList.get(position).getMain().getTempMax())+ AppConstants.DEGREE_SYMBOL_UNICODE);
        }
        if (!TextUtils.isEmpty(dataList.get(position).getDtTxt())){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            long daysDiff = AppUtils.getDaysFromDate(calendar.getTime(),AppUtils.convertStringToDate(dataList.get(position).getDtTxt(),AppConstants.DATE_FORMAT_WEATHER_API));
            if (daysDiff == 0)
                holder.txtDay.setText("Today");
            else if (daysDiff == 1)
                holder.txtDay.setText("Tomorrow");
            else
                holder.txtDay.setText(AppUtils.convertDateFormat(dataList.get(position).getDtTxt(),AppConstants.DATE_FORMAT_WEATHER_API, AppConstants.DATE_FORMAT_RECYCLER_VIEW));
        }

        if (!TextUtils.isEmpty(dataList.get(position).getWeather().get(0).getMain())){
            holder.txtWeatherState.setText(dataList.get(position).getWeather().get(0).getMain());
            if (dataList.get(position).getWeather().get(0).getMain().equals("Clouds")){
                holder.imgWeather.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.clouds));
            } else if (dataList.get(position).getWeather().get(0).getMain().equals("Clear")){
                holder.imgWeather.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.clear));
            } else if (dataList.get(position).getWeather().get(0).getMain().equals("Rain")){
                holder.imgWeather.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.rain));
            }else if (dataList.get(position).getWeather().get(0).getMain().equals("Storm")){
                holder.imgWeather.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.storm));
            }else if (dataList.get(position).getWeather().get(0).getMain().equals("Sun")){
                holder.imgWeather.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.sun));
            }else{
                holder.imgWeather.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.clear));
            }
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

        private TextView txtDay,txtTemperature,txtWeatherState;
        private ImageView imgWeather;
        private RelativeLayout layoutWeather;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txt_day);
            txtTemperature = itemView.findViewById(R.id.txt_temperature);
            imgWeather = itemView.findViewById(R.id.imgWeather);
            txtWeatherState = itemView.findViewById(R.id.txtWeatherState);
//            layoutWeather = itemView.findViewById(R.id.layout_weather);
        }
    }
}
