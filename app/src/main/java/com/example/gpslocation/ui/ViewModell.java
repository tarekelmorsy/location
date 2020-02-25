package com.example.gpslocation.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gpslocation.R;
import com.example.gpslocation.data.Client;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.SupportwdLocation;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewModell extends ViewModel {
    MutableLiveData<SupportwdLocation> mutableLiveData = new MutableLiveData<>();
    public void getData() {
        Client.getINSTANCE().getData(MapsActivity.CAMERAPOSITION.target.latitude,MapsActivity.CAMERAPOSITION.target.longitude).enqueue(new Callback<SupportwdLocation>() {
            @Override
            public void onResponse(Call<SupportwdLocation> call, Response<SupportwdLocation> response) {

                if(response.isSuccessful()){
                    mutableLiveData.setValue(response.body());

                    MapsActivity.TXSUCCESSFUL.setText(response.body().getResult().getCity().getNameAr() + "," + response.body().getResult().getCountry().getNameAr());

                    MapsActivity.TXSEARCH.setText(response.body().getResult().getName()+response.body().getResult().getCity().getName());

                    MapsActivity.TXSUCCESSFUL.setBackgroundResource(R.drawable.bgtxissuccessful);
                }
                else {
                    try {
                        ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(),ErrorResponse.class);
                        MapsActivity.TXSUCCESSFUL.setText(errorResponse.getMessageAr());
                        MapsActivity.TXSUCCESSFUL.setBackgroundResource(R.drawable.bgtxnotsuccessful);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<SupportwdLocation> call, Throwable t) {


            }
        });}
}