![GitHub](https://img.shields.io/github/license/ShaniHalali/SDK_ADS_Android_Library)
[![](https://jitpack.io/v/ShaniHalali/SDK_ADS_Android_Library.svg)](https://jitpack.io/#ShaniHalali/SDK_ADS_Android_Library)
[![API](https://img.shields.io/badge/API-26%2B-green.svg?style=flat)]()

# 📱 Android Ads SDK - Location-Based Smart Ads

This SDK helps Android developers integrate **smart and dynamic ads** that are:

- 📍 **Location-aware** - ads are filtered by the user’s current city
- 🎯 **Category-targeted** - Hotels, Restaurants, Products, Attractions
- 🖼️ Supports both **image ads** and **video ads**
- 📊 Automatically tracks key events: views, clicks, and completed video views

- 🌐 Includes a [**web-based dashboard**](https://portal-ads-dashboard-react-typejs.vercel.app/) for monitoring ad performance, 
You can find more details and documentation on [*Ads portal repository*](https://github.com/ShaniHalali/Portal_ads_dashboard_REACT_TYPEJS)

---
https://github.com/user-attachments/assets/f75316f6-2324-4019-a774-e06dd7c13aff

https://github.com/user-attachments/assets/085f1b9d-bc14-4f4b-b221-331301c70849

---
## 🌐 API – Flask Server

The Flask backend exposes a set of RESTful endpoints to manage ads, including creation, updates, filtering, and usage tracking. All endpoints are prefixed with `/ad_sdk`.

### 🔧 Core Ad Management Endpoints

| Endpoint                              | Method | Description                                                  |
|---------------------------------------|--------|--------------------------------------------------------------|
| `/ad_sdk`                             | POST   | **Create a new ad** – Adds a new ad document to the database. |
| `/ad_sdk/<package_name>/all`          | GET    | **Get all ads** – Returns all ads stored for the specified app package. |
| `/ad_sdk/<package_name>/<ad_id>`      | GET    | **Get ad by ID** – Retrieves a single ad by ID and package name. |
| `/ad_sdk/<package_name>/<ad_id>`      | PUT    | **Update ad by ID** – Updates fields of an ad by ID.         |
| `/ad_sdk/<package_name>`              | GET    | **Get ads by filters** – Returns all ads active on a given date, optionally filtered by `location` and `category`. |
| `/ad_sdk`                             | DELETE | **Delete all ads** – Removes all ads from the database (for dev/test purposes). |

---

### 📊 Tracking & Analytics Endpoints

| Endpoint                                                    | Method | Description                                                                          |
|-------------------------------------------------------------|--------|--------------------------------------------------------------------------------------|
| `/ad_sdk/<ad_id>/click?package_name=...`                    | POST   | **Record ad click** – Increments the click count for the given ad and app.          |
| `/ad_sdk/<ad_id>/view?package_name=...&category=...`        | POST   | **Record ad view** – Increments the view count for the ad, based on app and category. |
| `/ad_sdk/<ad_id>/view/completed?package_name=...`           | POST   | **Record completed view** – Specifically for video ads. Tracks full views.          |
| `/ad_sdk/AdClickStats/summary`                              | GET    | **Get summarized stats** – Aggregates total clicks, views, and completed views.     |

---

For more information and full request/response formats, check the [*Ads flask server repository*](https://github.com/ShaniHalali/SDK_ADS_Android_Library).


---
## 🌍 Get User's City Name for Location-Based Ads

The SDK uses Android's location service to determine the user's city and fetch location-relevant ads.

### 💡 Notes
- The SDK does **not** request location permission automatically.
- You **must** request permission at runtime before using location-based features.
- It uses `ACCESS_COARSE_LOCATION` (WiFi/network-based) **no GPS needed**.
- You should handle fallback logic (e.g., default city like "Tel Aviv").

---
## Setup
Step 1. Add it in your root build.gradle at the end of repositories:
```xml
allprojects {
    repositories {
        // other repositories
        maven { url = uri("https://jitpack.io" )}
    }
}
```
Step 2. Add the dependency:
```xml
dependencies {
    implementation("com.github.ShaniHalali:SDK_ADS_Android_Library:1.0.0") // Include the AdsLib library from GitHub
}
```
---
## Requirements (for full SDK integration)

### Permissions & Setup
1. Add this to your `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```
2. Add the following to your layout XML.

Make sure to add it after the main content layout (e.g., ScrollView or RecyclerView) so that it appears on top or at the bottom of the screen, depending on your layout structure.
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
| `adsManager.showRandomAdFromByLocation(city)` | `String city'     | Displays a random ad based on the provided city    |
| `adsManager.showHotelsAd(city)`         | `String city` (nullable)      | Displays a hotel-related ad for the given city. If city is null, shows any hotel ad.|
| `adsManager.showRestaurantsAd(city)`    | `String city` (nullable)       | Displays a restaurant-related ad for the given city. If city is null, shows any restaurant ad.|
| `adsManager.showProductsAd(city)`       | `String city` (nullable)      | Displays a product-related ad for the given city. If city is null, shows any product ad.|
| `adsManager.showExitButton(seconds)`    | `int seconds`       | Shows a close button after delay - you need to set the secounds as you prefer         |



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
    private String userCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adView = findViewById(R.id.adView);
        adsManager = new AdsManager(this); // Uses your app's package name automatically, this-for Activity /getContext()-for fragment
        adsManager.setAdView(adView);
        adView.setAdsManager(adsManager);


        adsManager.showExitButton(10); // 10 seconds for example
    }
    // one of five function that the developer can use
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
    //------- for Fragment!!!------
     @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        } else {
            getUserCityAndLoadAd();
        }
    }

    //------- for Activity!!!------
         @Override
    public void onResume() {
        super.onResume();
       if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        } else {
            getUserCityAndLoadAd();
        }
    }
}
```




