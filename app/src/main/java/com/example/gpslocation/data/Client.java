package com.example.gpslocation.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final String BASE_URL = "https://testing.sary.co/api/location/";
    private ApiInterface apiInterface;
    private static Retrofit INSTANCE;




    public static Retrofit getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INSTANCE;
    }


}
