package com.example.gpslocation.data;

import com.example.gpslocation.model.SupportwdLocation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("locate")
    public Call<SupportwdLocation> gitSuccessful(@Query("lat") Double lat, @Query("lng") Double lng);



}
