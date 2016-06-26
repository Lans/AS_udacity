package com.example.weather.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weather.BusProvider;
import com.example.weather.R;
import com.example.weather.data.daily_forecast;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/23.
 */
public class HomeFragment extends Fragment {
    @Bind(R.id.max_tmp)
    TextView maxTmp;
    @Bind(R.id.wind_power)
    TextView windpower;
    @Bind(R.id.quality)
    TextView quality;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.dailys)
    RecyclerView dailys;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        //RecyclerView的设置
        dailys.setHasFixedSize(true);
        dailys.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        dailys.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void getListMessage(SendListEvent event) {
        Map<String, Object> map = event.getMap();

        Object forecastList = map.get("forecastList");
        dailys.setAdapter(new HomeFragmentAdapter((List<daily_forecast>) forecastList));

        Bundle bundle = (Bundle) map.get("toDayBundle");
        name.setText(bundle.getString("name"));
        maxTmp.setText(bundle.getString("maxTmp") + "°");
        windpower.setText(bundle.getString("windpower"));
        quality.setText(bundle.getString("quality"));

        windpower.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.wind_power, 0, 0, 0);
        quality.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.quality, 0, 0, 0);
    }
}