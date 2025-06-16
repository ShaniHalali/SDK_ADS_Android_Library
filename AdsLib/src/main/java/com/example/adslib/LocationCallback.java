package com.example.adslib;

import android.location.Location;

public interface LocationCallback {
    void onLocationReceived(Location location);
    void onLocationError(String error);
}
