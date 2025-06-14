package com.example.adslib;

import java.util.List;

public class AdsManager {
    // This class is responsible for managing ads in the application.
    // It can include methods to initialize ads, load ads, and handle ad events.
    private final String hotelsAdsPackageName = "Hotels.ads";
    private final String restaurantsAdsPackageName = "Restaurants.ads";
    private final String sellProductsAdsPackageName = "SellProducts.ads";
    AdController controller = new AdController();
    private AdView adView;


    public void setAdView(AdView adView) {
        this.adView = adView;
    }

    public void showRAdFromPackageName(String packageName) {
        // Logic to display the ad
        controller.fetchOneActiveAd(packageName,null,null, new Callback_Ads() {
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

    public void showHotelsAd(){
        controller.fetchOneActiveAd(hotelsAdsPackageName,null,null, new Callback_Ads() {
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

    public void showRestaurantsAd(){
        controller.fetchOneActiveAd(restaurantsAdsPackageName,null,null, new Callback_Ads() {
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

    public void showSellProductsAd(){
        controller.fetchOneActiveAd(sellProductsAdsPackageName,null,null, new Callback_Ads() {
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

        controller.fetchOneActiveAd(hotelsAdsPackageName,null,location, new Callback_Ads() {
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

    //המפתח יחליט מה קורה כאשר המשתמש מחליט ללחוץ על הכפתור של הפרסומה
    public void onAdClicked(Ad ad) {
        // Logic to handle ad click events
        // This could include tracking the click or redirecting the user to the ad link


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

}
