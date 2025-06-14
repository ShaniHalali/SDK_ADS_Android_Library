package com.example.adslib;

import java.util.List;

public interface Callback_Ads {
    void ready(Ad ad);
    void readyList(List<Ad> ads);
    void failed(String Message);

}
