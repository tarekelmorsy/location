package com.example.gpslocation.data;

import com.example.gpslocation.model.BasketLocation;
import com.example.gpslocation.model.CodeModel;
import com.example.gpslocation.model.SupportwdLocation;
import com.example.gpslocation.model.SupportwdLocationDetails;
import com.example.gpslocation.model.UserPhoneModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("location/locate")
    public Observable<SupportwdLocation> gitSuccessful(@Query("lat") Double lat, @Query("lng") Double lng);


    @Headers({
            "Accept-Language: ar",
            "Content-Type: application/json",
            "App-Version: 2.7.1.2.0",
            "device-type: android"
    })
    @POST("baskets/")
    public Observable<BasketLocation> fetchSupportwdLocationDetail(@Body SupportwdLocationDetails supportwdLocationDetails);

    @Headers({
            "app-version: 2.8.1.1"
    })
    @GET("users/user/login")
    public Observable<UserPhoneModel> gitUserCode(@Query("user_phone") String userPhone);
    @Headers({
            "app-version: 2.8.1.1"
    })
    @FormUrlEncoded
    @POST("users/user/login")
    public Observable<CodeModel> loginbycode(@Field("user_phone") String userPhone, @Field("code") String code);

}
