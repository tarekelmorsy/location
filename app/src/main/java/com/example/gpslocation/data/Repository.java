package com.example.gpslocation.data;

import androidx.lifecycle.MutableLiveData;

import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.SupportwdLocation;
import com.google.gson.Gson;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository {

    private MutableLiveData<SupportwdLocation> mutableLiveData = new MutableLiveData<>();

    private MutableLiveData<ErrorResponse> errorResponseMutableLiveData = new MutableLiveData<>();

    private static Repository INSTANCE;
    private ApiInterface apiInterface;



    public static Repository getINSTANCEe() {
        if (null == INSTANCE){
            INSTANCE = new Repository();

        }


        return INSTANCE;
    }

    private Repository() {
        Retrofit retrofit = Client.getINSTANCE();
      apiInterface= retrofit.create(ApiInterface.class);
    }


    public void getData( Double lat,Double lon) {
   apiInterface.gitSuccessful(lat,lon  ).enqueue(new Callback<SupportwdLocation>() {
            @Override
            public void onResponse(Call<SupportwdLocation> call, Response<SupportwdLocation> response) {

                if(response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
                else {
                    try {
                        ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(),ErrorResponse.class);

                        errorResponseMutableLiveData.postValue(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<SupportwdLocation> call, Throwable t) {


            }
        });
    }


    public MutableLiveData <SupportwdLocation> getMutableLiveDataSupportwdLocation(){
        return mutableLiveData;
    }

    public MutableLiveData <ErrorResponse> getMutableLiveDataeror(){

        return errorResponseMutableLiveData;
    }
}
