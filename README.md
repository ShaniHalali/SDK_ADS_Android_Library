# 📱 Android Ads SDK - Location-Based Smart Ads

This SDK helps Android developers integrate **smart and dynamic ads** that are:

- 📍 **Location-aware** — ads are filtered by the user’s current city
- 🎯 **Category-targeted** — Hotels, Restaurants, Products, Attractions
- 🖼️ Supports both **image ads** and **video ads**
- 📊 Automatically tracks key events: views, clicks, and completed video views

---

## 🌍 Get User's City Name for Location-Based Ads

The SDK uses Android's location service to determine the user's city and fetch location-relevant ads.

### 💡 Notes
- The SDK does **not** request location permission automatically.
- You **must** request permission at runtime before using location-based features.
- It uses `ACCESS_COARSE_LOCATION` (WiFi/network-based) — **no GPS needed**.
- You should handle fallback logic (e.g., default city like "Tel Aviv").

---

## 📋 Requirements (for full SDK integration)

### ✅ Permissions & Setup

1. Add this to your `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

2.Add the following to your layout XML:
```xml
<com.example.adslib.AdView
    android:id="@+id/adView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

3.Set up the AdView and AdsManager in your Activity, and pass the Context to AdsManager so your app's package name is used.

4.Handle runtime permission + ad loading logic


## 📊 Automatic Tracking (No Setup Needed)

The SDK automatically tracks and reports the following events:

| Event              | Triggered When                                 |
|-------------------|-------------------------------------------------|
| `onAdStarted()`   | Ad is loaded and begins displaying              |
| `onAdClicked()`   | User clicks on the ad link                      |
| `onAdCompleted()` | User finishes watching a video ad               |

Each event is reported to the backend using the **real app's package name** via `context.getPackageName()`.  
➡️ **No manual setup is needed** — tracking is handled internally by the SDK.

## 🧰 Available Ad Functions

| Function                                | Parameters         | Description                                         |
|-----------------------------------------|--------------------|-----------------------------------------------------|
| `adsManager.showRandomAd()`             | –                  | Displays a random ad randomaliy location and category |
| `adsManager.showRandomAdFromByLocation(city)` | `String city'       | Displays a random ad based on the provided city     |
| `adsManager.showHotelsAd(city)`         | `String city`       | Displays a hotel related ad for the given city      |
| `adsManager.showRestaurantsAd(city)`    | `String city`       | Displays a restaurant-related ad for the given city |
| `adsManager.showProductsAd(city)`       | `String city`       | Displays a product-related ad for the given city    |
| `adsManager.showExitButton(seconds)`    | `int seconds`       | Shows a close button after delay - you neet to set the secounds as you prefer         |



## 🧩 Integration Notes
SDK is fully self-contained.

No backend changes required.

Ads are fetched from a global "Ads" collection.

Every app is tracked separately using its unique package name.

## 🧑‍💻 Full Usage Example
```xml
public class MainActivity extends AppCompatActivity {
    private AdView adView;
    private AdsManager adsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adView = findViewById(R.id.adView);
        adsManager = new AdsManager(this); // Uses your app's package name automatically
        adsManager.setAdView(adView);
        adView.setAdsManager(adsManager);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        } else {
            getUserCityAndLoadAd();
        }

        adsManager.showExitButton(10); // Optional
    }

    private void getUserCityAndLoadAd() {
        LocationHelper.getCityNameFromDevice(this, new CityCallback() {
            @Override
            public void onCityLoaded(String city) {
                adsManager.showRandomAdFromByLocation(city);
            }

            @Override
            public void onError(String reason) {
                adsManager.showRandomAdFromByLocation("Tel Aviv"); // Fallback
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getUserCityAndLoadAd();
        } else {
            adsManager.showRandomAdFromByLocation("Tel Aviv"); // Fallback
        }
    }
}
```




