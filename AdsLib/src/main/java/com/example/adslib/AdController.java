package com.example.adslib;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
}

