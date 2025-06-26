package com.example.adssdkproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.adslib.Ad;
import com.example.adslib.AdController;
import com.example.adslib.AdView;
import com.example.adslib.AdsManager;
import com.example.adslib.Callback_Ads;
import com.example.adslib.CityCallback;
import com.example.adslib.LocationHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private TextView adTextView;
private AdView adView;
private String userCity;
private AdsManager adsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView = findViewById(R.id.adView);
        adsManager = new AdsManager(this);
        adsManager.setAdView(adView);
        adView.setAdsManager(adsManager);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        } else {
            getUserCityAndLoadAd();
        }

        //adsManager.showHotelsAd();
       // adsManager.showRestaurantsAd(null);
        //adsManager.showRandomAdFromByLocation("Tel Aviv");
        adsManager.showExitButton(10);

    }

    private void getUserCityAndLoadAd() {
        LocationHelper.getCityNameFromDevice(this, new CityCallback() {
            @Override
            public void onCityLoaded(String city) {
                userCity = city;
                if(userCity == null || userCity.isEmpty()) {
                    userCity = "Tel Aviv"; // Fallback city if none found
                }
                Log.d("CITY_RESULT", "User's city: " + userCity);
                adsManager.showRandomAdFromByLocation(userCity);
            }

            @Override
            public void onError(String reason) {
                Log.e("CITY_ERROR", "Could not get city: " + reason);
                adsManager.showRandomAdFromByLocation("Tel Aviv"); //default -for example- tel aviv
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //user permission for location
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getUserCityAndLoadAd();
        } else {
            Log.e("CITY_ERROR", "Permission denied – showing fallback ad");
            adsManager.showRandomAdFromByLocation("Tel Aviv"); //default
        }
    }
}
