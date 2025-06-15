package com.example.adssdkproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.adslib.Ad;
import com.example.adslib.AdController;
import com.example.adslib.AdView;
import com.example.adslib.AdsManager;
import com.example.adslib.Callback_Ads;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private TextView adTextView;
private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView = findViewById(R.id.adView);

        AdsManager adsManager = new AdsManager();
        adsManager.setAdView(adView);
        adsManager.showRandomAdFromByLocation("Tel Aviv");
        adsManager.showExitButton(10);

    }
    }
