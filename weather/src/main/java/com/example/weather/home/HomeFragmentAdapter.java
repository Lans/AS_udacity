package com.example.weather.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.data.daily_forecast;
import com.example.weather.utils.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.MyViewHolder> {
    private List<daily_forecast> daily_forecastList;

    public HomeFragmentAdapter(List<daily_forecast> daily_forecastList) {
        this.daily_forecastList = daily_forecastList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_daily, parent, false);
        MyViewHolder holder = new MyViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        daily_forecast daily_forecast = daily_forecastList.get(position);
        if (daily_forecast.getDate().equals(DateUtil.getCurrentDate())) {
            holder.date.setText("今天");
        } else {
            holder.date.setText(DateUtil.getWeek(daily_forecast.getDate()));
        }
        holder.dailys_max_min.setText(daily_forecast.getTmp().getMax() + "°/" + daily_forecast.getTmp().getMin() + "°");
        String txt_d = daily_forecast.getCond().getTxt_d();
        fromStringToImgage(holder, txt_d);

    }

    private void fromStringToImgage(MyViewHolder holder, String txt_d) {
        switch (txt_d) {
            case "中雨":
                holder.dailys_img.setImageResource(R.mipmap.moderate_rain);
                break;
            case "小雨":
                holder.dailys_img.setImageResource(R.mipmap.light_rain);
                break;
            case "大雨":
                holder.dailys_img.setImageResource(R.mipmap.heavy_rain);
                break;
            case "阵雨":
                holder.dailys_img.setImageResource(R.mipmap.shower_rain);
                break;
            case "雷阵雨":
                holder.dailys_img.setImageResource(R.mipmap.thundershower);
                break;
            case "阴":
                holder.dailys_img.setImageResource(R.mipmap.overcast);
                break;
            case "晴":
                holder.dailys_img.setImageResource(R.mipmap.sunny);
                break;
            case "多云":
                holder.dailys_img.setImageResource(R.mipmap.cloudy);
                break;
            case "毛毛雨/细雨":
                holder.dailys_img.setImageResource(R.mipmap.extreme_rain);
                break;
            default:
                holder.dailys_img.setImageResource(R.mipmap.unknown);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return daily_forecastList.size() - 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView date, dailys_max_min;
        public ImageView dailys_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            dailys_max_min = (TextView) itemView.findViewById(R.id.dailys_max_min);
            dailys_img = (ImageView) itemView.findViewById(R.id.dailys_img);
        }
    }
}
