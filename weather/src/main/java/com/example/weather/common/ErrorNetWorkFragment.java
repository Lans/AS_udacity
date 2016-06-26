package com.example.weather.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.weather.BusProvider;
import com.example.weather.R;
import com.example.weather.home.HomeActivity;
import com.example.weather.home.HomePresenter;
import com.squareup.otto.Bus;

/**
 * Created by Administrator on 2016/6/3.
 */
public class ErrorNetWorkFragment extends Fragment {
    private Button retry_Btn = null;
    private HomePresenter homePresenter;
    private HomeActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.error_network, container, false);
        activity = (HomeActivity) getActivity();
        homePresenter = new HomePresenter(activity);
        retry_Btn = (Button) view.findViewById(R.id.retry_Btn);
        retry_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.getJsonData(activity.City);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }
}
