package com.example.adslib;

public interface CityCallback {
    void onCityLoaded(String city);
    void onError(String reason);
}
