package com.example.adslib;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationHelper {

    public static boolean hasLocationPermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
    }
    public static void getUserLocation(Context context, LocationCallback callback) {
        if (!hasLocationPermission(context)) {
            callback.onLocationError("Location permission not granted");
            return;
        }

        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context);


        LocationRequest locationRequest = new LocationRequest.Builder(
                PRIORITY_HIGH_ACCURACY,
                1000 // interval in milliseconds
        )
                .setMinUpdateIntervalMillis(1000)
                .setWaitForAccurateLocation(false)
                .setMaxUpdateDelayMillis(5000)
                .setDurationMillis(5000)
                .setMaxUpdates(1)
                .build();
        com.google.android.gms.location.LocationCallback locationCallback = new com.google.android.gms.location.LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null || locationResult.getLastLocation() == null) {
                    callback.onLocationError("Location not available");
                    Log.d("LocationError", "LocationResult is null or last location is null");
                    return;
                }

                Location location = locationResult.getLastLocation();
                Log.d("LocationReceived", "Location received: " + location.toString());
                callback.onLocationReceived(location);

                fusedLocationClient.removeLocationUpdates(this);
            }
        };

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException e) {
            callback.onLocationError("SecurityException: " + e.getMessage());
        }
    }


    private static String getCityFromLocation(Context context, Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1); //Num of address

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String CityName= address.getLocality();
                if(CityName == null){
                    CityName="Kfar Tavor";
                }
                Log.d("TAGCityName", CityName != null ? CityName : "cityName is null");
                return CityName; // return the city name - of the user
            } else  {
                Log.w("AdSDK", "No address found for location");
                return null;
            }
        } catch (IOException e) {
            Log.e("AdSDK", "Geocoder failed: " + e.getMessage());
            return null;
        }
    }

    public static void getCityNameFromDevice(Context context, CityCallback callback) {
        getUserLocation(context, new LocationCallback() {
            @Override
            public void onLocationReceived(Location location) {
                String city = getCityFromLocation(context, location);
                if (city != null) {
                    callback.onCityLoaded(city);
                } else {
                    callback.onError("City not found");
                }
            }

            @Override
            public void onLocationError(String error) {
                callback.onError(error);
            }
        });
    }

    public static String getCityName(Context context, Location location) {
        if (!hasLocationPermission(context)) {
            Log.e("AdSDK", "Location permission not granted – please request it before using AdSDK.getCityName(context)");
            return null;
        }
                return getCityFromLocation(context,location);
    }

}
