package com.example.adslib;

import android.content.Context;

import java.util.List;

public class AdsManager {
    // This class is responsible for managing ads in the application.
    // It can include methods to initialize ads, load ads, and handle ad events.
    private final String adsPackageName = "Ads";
    private final String HotelCategory = "Hotel";
    private final String restaurantsCategory = "Restaurant";
    private final String ProductsCategory = "Product";
    private final String AttractionCategory = "Attraction";
    private final String appName;

    AdController controller = new AdController();
    private AdView adView;

    public AdsManager(Context context) {
        this.appName = context.getPackageName();
    }

    public void setAdView(AdView adView) {
        this.adView = adView;
    }

    public void showRandomAd() {
        // Logic to display the ad
        controller.fetchOneActiveAd(adsPackageName,null,null,null, new Callback_Ads() {
            @Override
            public void ready(Ad ad) {
                adView.loadAd( ad);
            }

            @Override
            public void readyList(List<Ad> ads) {
                // Handle the list of ads if needed
            }

            @Override
            public void failed(String Message) {
                // Handle the failure case

            }
        });

    }

    public void showHotelsAd(String Location){
        controller.fetchOneActiveAd(adsPackageName,null,Location,HotelCategory, new Callback_Ads() {
            @Override
            public void ready(Ad ad) {
                adView.loadAd( ad);
            }

            @Override
            public void readyList(List<Ad> ads) {
                // Handle the list of ads if needed
            }

            @Override
            public void failed(String Message) {
                // Handle the failure case

            }
        });

    }

    public void showRestaurantsAd(String Location){
        controller.fetchOneActiveAd(adsPackageName,null,Location,restaurantsCategory, new Callback_Ads() {
            @Override
            public void ready(Ad ad) {
                adView.loadAd( ad);
            }

            @Override
            public void readyList(List<Ad> ads) {
                // Handle the list of ads if needed
            }

            @Override
            public void failed(String Message) {
                // Handle the failure case

            }
        });

    }

    public void showProductsAd(String Location){
        controller.fetchOneActiveAd(adsPackageName,null,Location,ProductsCategory, new Callback_Ads() {
            @Override
            public void ready(Ad ad) {
                adView.loadAd( ad);
            }

            @Override
            public void readyList(List<Ad> ads) {
                // Handle the list of ads if needed
            }

            @Override
            public void failed(String Message) {
                // Handle the failure case

            }
        });

    }


    public void showRandomAdFromByLocation(String location) {
        // Logic to display a random ad based on the provided location

        controller.fetchOneActiveAd(adsPackageName,null,location,null, new Callback_Ads() {
            @Override
            public void ready(Ad ad) {
                adView.loadAd( ad);
            }

            @Override
            public void readyList(List<Ad> ads) {
                // Handle the list of ads if needed
            }

            @Override
            public void failed(String Message) {
                // Handle the failure case

            }
        });

    }

    public void onAdClosed(Ad ad) {
        // Logic to handle ad close events
        // This could include cleaning up resources or updating the UI
        if (adView != null) {
            adView.closeAdView();
        }
    }
    public void showExitButton(int seconds) {
        if (adView != null) {
            adView.showExitButtonWithDelay(seconds);
        }
    }
    public void onAdClicked(Ad ad) {
        controller.recordClick(ad.get_id(), appName);
    }
    public void onAdStarted(Ad ad) {
        controller.recordView(ad.get_id(), appName,ad.getCategory());
    }
    public void onAdCompleted(Ad ad) {
        controller.recordCompletedView(ad.get_id(), appName);
    }


}
