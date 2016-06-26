package com.example.weather.data;

import java.util.List;

/**
 * Created by Ls on 2016/5/18.
 */
public class Weather {
    private Aqi aqi;
    private Basic basic;
    private Now now;
    private List<daily_forecast> daily_forecast;

    public List<daily_forecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<daily_forecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }
}
