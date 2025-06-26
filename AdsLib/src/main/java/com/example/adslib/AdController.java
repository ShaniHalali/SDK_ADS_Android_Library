package com.example.adslib;


import android.util.Log;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdController {
    private static final String BASE_URL = "https://ad-sdk-flask-api.vercel.app/";


    public AdController setCallback_ads(Callback_Ads callback_ads) {
        this.callback_ads = callback_ads;
        return this;
    }


    private Callback_Ads callback_ads;
    private AdsAPI getAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AdsAPI.class);
    }

    public void fetchOneActiveAd(String packageName, String date, String location, String category, Callback_Ads callbackAds) {
        AdsAPI adsAPI = getAPI();
        Call<List<Ad>> call = adsAPI.getActiveAds(packageName, date, location, category);

        call.enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, retrofit2.Response<List<Ad>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<Ad> ads = response.body();

                    Iterator<Ad> iterator = ads.iterator();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

                    while (iterator.hasNext()) {
                        Ad ad = iterator.next();
                        long now = System.currentTimeMillis();

                        try {
                            Date beginDate = dateFormat.parse(ad.getBeginning_date());
                            Date expireDate = dateFormat.parse(ad.getExpiration_date());

                            if (beginDate != null && expireDate != null &&
                                    beginDate.getTime() < now && expireDate.getTime() > now) {
                                Log.d("AdController", "Ad is active: " + ad.get_id());
                            } else {
                                Log.d("AdController", "Ad is not active: " + ad.get_id());
                                iterator.remove();
                            }
                        } catch (ParseException e) {
                            Log.e("AdController", "Failed to parse date for ad: " + ad.get_id(), e);
                            iterator.remove(); // Remove if parsing fails
                        }
                    }

                    int numberOfAds = ads.size();
                    if(numberOfAds >= 1) {

                        //random one ad
                        int randomIndex = (int) (Math.random() * numberOfAds);
                        Ad ad = ads.get(randomIndex);
                        callbackAds.ready(ad);
                    }
                } else {
                    callbackAds.failed("No ads found");
                }
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {
                callbackAds.failed("Error: " + t.getMessage());
            }
        });
    }

    public void fetchAllAds(String packageName, Callback_Ads callback) {
        setCallback_ads(callback_ads);
        Call<List<Ad>> call = getAPI().loadAllAds(packageName);
        call.enqueue(listCallback);
    }
    private Callback<List<Ad>> listCallback = new Callback<List<Ad>>() {
        @Override
        public void onResponse(Call<List<Ad>> call, retrofit2.Response<List<Ad>> response) {
            if (response.isSuccessful() && response.body() != null) {
                callback_ads.readyList(response.body());
            }
        }

        @Override
        public void onFailure(Call<List<Ad>> call, Throwable t) {
            callback_ads.failed("Error: " + t.getMessage());
        }
    };

    //records ads
    public void recordClick(String adId, String packageName) {
        getAPI().recordAdClick(adId, packageName).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("AdController", "Click recorded: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AdController", "Click failed: " + t.getMessage());
            }
        });
    }

    public void recordView(String adId, String packageName, String category) {
        getAPI().recordAdView(adId, packageName, category).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("AdController", "View recorded: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AdController", "View failed: " + t.getMessage());
            }
        });
    }

    public void recordCompletedView(String adId, String packageName) {
        getAPI().recordCompletedView(adId, packageName).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("AdController", "Completed view recorded: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AdController", "Completed view failed: " + t.getMessage());
            }
        });
    }

}

