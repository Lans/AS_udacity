package com.example.weather.home;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/31.
 */
public class SendListEvent {
    private Map<String, Object> map;

    public SendListEvent(Map<String, Object> map) {
        this.map = map;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
