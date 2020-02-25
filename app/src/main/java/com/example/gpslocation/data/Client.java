package com.example.gpslocation.data;

import com.example.gpslocation.model.SupportwdLocation;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final String BASE_URL = "https://testing.sary.co/api/location/";
    private ApiInterface apiInterface;
    private static Client INSTANCE;

    public Client() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static Client getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new Client();
        }
        return INSTANCE;
    }

    public Call<SupportwdLocation> getData(Double lat , Double lng){
        return apiInterface.gitSuccessful(lat,lng);
    }
}
