## 🌍 Get User's City Name for Location-Based Ads

This SDK allows you to get the user's **current city** and display **location-based ads** using that information.

---

### 💡 Notes

- The SDK does **not** request location permission automatically.
- You **must** request permission at runtime before calling `LocationHelper.getCityNameFromDevice(...)`.
- The SDK uses Android's `ACCESS_COARSE_LOCATION` (WiFi/network-based), and does **not require GPS**.
- If location is unavailable or permission denied, fallback logic (e.g. showing a default ad) is recommended.

---
### 📋 Requirements

1. **Add this permission** to your app's `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

2. **Request location permission at runtime** before using any location-based features:

```java
if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
} else {
    getUserCityAndLoadAd();
}
```

3. **Handle permission result** in your `Activity`:

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 101 && grantResults.length > 0 &&
        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        getUserCityAndLoadAd();
    } else {
        adsManager.showRandomAdFromByLocation("Tel Aviv"); // fallback ad
    }
}
```

---

### 🧑‍💻 Usage Example

```java
private AdsManager adsManager;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    AdView adView = findViewById(R.id.adView);
    adsManager = new AdsManager();
    adsManager.setAdView(adView);

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
    } else {
        getUserCityAndLoadAd();
    }

    adsManager.showExitButton(10); // optional
}

private void getUserCityAndLoadAd() {
    LocationHelper.getCityNameFromDevice(this, new CityCallback() {
        @Override
        public void onCityLoaded(String city) {
            adsManager.showRandomAdFromByLocation(city);
        }

        @Override
        public void onError(String reason) {
            adsManager.showRandomAdFromByLocation("Tel Aviv"); // fallback
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
        adsManager.showRandomAdFromByLocation("Tel Aviv"); // fallback
    }
}
```

---


