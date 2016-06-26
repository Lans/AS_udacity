package com.example.weather.data;

/**
 * Created by Administrator on 2016/5/26.
 */
public class daily_forecast {
    private String date;

    private Tmp tmp;
    private Daily_Cond cond;

    public Daily_Cond getCond() {
        return cond;
    }

    public void setCond(Daily_Cond cond) {
        this.cond = cond;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
