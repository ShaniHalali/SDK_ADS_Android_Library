package com.example.adslib;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface AdsAPI {
    @GET("/ad_sdk/<package_name>/all")
    Call<List<Ad>> loadAllAds(
            @Path(value = "package_name",encoded = true) String _packageName
    );

    @GET("/ad_sdk/{package_name}")
    Call<List<Ad>> getActiveAds(
            @Path(value = "package_name", encoded = true) String packageName,
            @Query("date") String date,                 // date example: "2023-10-01"
            @Query("location") String location          // location example: "Tel Aviv"
    );


}

