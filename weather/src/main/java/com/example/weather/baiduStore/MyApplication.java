package com.example.weather.baiduStore;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by Ls on 2016/5/15.
 */
public class MyApplication extends Application {
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiStoreSDK.init(this,"7eb0891103e02ddb83ce10a05b1b111d");
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }
}
