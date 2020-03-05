package com.example.gpslocation.data;

import androidx.lifecycle.MutableLiveData;

import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.SupportwdLocation;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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



    public MutableLiveData <SupportwdLocation> getMutableLiveDataSupportwdLocation(){
        return mutableLiveData;
    }

    public MutableLiveData <ErrorResponse> getMutableLiveDataeror(){

        return errorResponseMutableLiveData;
    }


    public void getData(final Double lat, Double lon) {
        Observable observable = apiInterface.gitSuccessful(lat, lon)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());



        Observer<SupportwdLocation> observer1 = new Observer<SupportwdLocation>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SupportwdLocation supportwdLocations) {
                mutableLiveData.postValue( supportwdLocations);


                };


            @Override
            public void onError(Throwable e) {
                if (e instanceof RetrofitException) {
                    try {
                        RetrofitException retrofitException = (RetrofitException) e;
                        ErrorResponse errorResponse = retrofitException.getErrorBodyAs(ErrorResponse.class);
                        errorResponseMutableLiveData.postValue(errorResponse);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                    }
            @Override
            public void onComplete() {

            }

        };


        observable.subscribe(observer1);
    }


}
