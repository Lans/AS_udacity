package com.example.weather.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.weather.BusProvider;
import com.example.weather.R;
import com.example.weather.common.ErrorNetWorkFragment;
import com.example.weather.data.Weather;
import com.example.weather.data.daily_forecast;
import com.example.weather.utils.NetUtil;
import com.squareup.otto.Produce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/3.
 */
public class HomePresenter implements HomeContract {

    private Map<String, Object> map;
    private HomeActivity activity;
    private String url="http://apis.baidu.com/wxlink/here/here?lat=39.9928&lng=116.396&cst=1 -H 'apikey:您自己的apikey'";
    public HomePresenter(HomeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getJsonData(String city) {
        Parameters p = new Parameters();
        p.put("city", city);

        if (NetUtil.isNetworkavailable(activity)) {
            if (activity.HomeLayout.findViewById(R.id.toolbar) == null) {
                activity.HomeLayout.addView(activity.toolbar, 0);
                activity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainLayout, new HomeFragment())
                        .commit();
            }
            ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, p, new ApiCallBack() {
                @Override
                public void onSuccess(int i, String s) {
                    super.onSuccess(i, s);
                    map = setJson(s);
                    BusProvider.getInstance().post(sendMessageEvent());
                }
            });
        } else {
            activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainLayout, new ErrorNetWorkFragment())
                    .commit();
            activity.HomeLayout.removeViewInLayout(activity.toolbar);
        }

    }

    private Map<String, Object> setJson(String s) {
        List<daily_forecast> forecastList = null;
        Map<String, Object> map = null;
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray HeWeather = jsonObject.getJSONArray("HeWeather data service 3.0");
        String s1 = JSON.toJSONString(HeWeather);
        List<Weather> weathers = JSONArray.parseArray(s1, Weather.class);
        for (Weather weather : weathers) {
            forecastList = weather.getDaily_forecast();
            Bundle bundle = new Bundle();
            bundle.putString("name", weather.getNow().getCond().getTxt());
            bundle.putString("maxTmp", weather.getNow().getTmp());
            bundle.putString("windpower", weather.getNow().getWind().getSc() + "级");
            bundle.putString("quality", weather.getAqi().getCity().getAqi() + " " + weather.getAqi().getCity().getQlty());

            map = new HashMap<>();
            map.put("forecastList", forecastList);
            map.put("toDayBundle", bundle);
        }
        return map;
    }

    @Produce
    public SendListEvent sendMessageEvent() {
        return new SendListEvent(map);
    }
}
