package com.example.gpslocation.data;

import androidx.lifecycle.MutableLiveData;

import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.BasketLocation;
import com.example.gpslocation.model.SupportwdLocation;
import com.example.gpslocation.model.SupportwdLocationDetails;

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
    private MutableLiveData<BasketLocation> basketMutableLiveData =new MutableLiveData<>();

    private static Repository INSTANCE;
    private static Repository INSTANCEDetail;

    private ApiInterface apiInterface;



    public static Repository getINSTANCEe() {
        if (null == INSTANCE){
            INSTANCE = new Repository();

        }


        return INSTANCE;
    }


    public static Repository getINSTANCEDetail() {
        if (null == INSTANCEDetail){
            INSTANCEDetail = new Repository();

        }


        return INSTANCEDetail;
    }



    private Repository() {

        Retrofit retrofit = Client.getINSTANCE();
      apiInterface= retrofit.create(ApiInterface.class);
    }



    public MutableLiveData <SupportwdLocation> getMutableLiveDataSupportwdLocation(){
        return mutableLiveData;
    }

    public MutableLiveData <BasketLocation> basketMutableLiveData(){
        return basketMutableLiveData;
    }

    public MutableLiveData <ErrorResponse> getMutableLiveDataeror(){

        return errorResponseMutableLiveData;
    }

    /**
     * get basket detail  from Support Location
     * @param supportwdLocationDetails
     */
    public void fetchTransactiondetail(SupportwdLocationDetails supportwdLocationDetails ){



        Observable observable= apiInterface.fetchSupportwdLocationDetail( supportwdLocationDetails )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<BasketLocation> locationObserver= new Observer<BasketLocation>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BasketLocation isSupportwdLocation) {
                basketMutableLiveData.setValue(isSupportwdLocation);
              //  Log.d("lllll",isSupportwdLocation.getMessage());


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(locationObserver);

    }

    /**
     * get Date from Support Location
     * @param lat
     * @param lon
     */
    public void getData(final Double lat, Double lon) {
        Observable observable = apiInterface.gitSuccessful(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;

        Observer<SupportwdLocation> observer1 = new Observer<SupportwdLocation>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SupportwdLocation supportwdLocations) {
                if (supportwdLocations.getStatus()) {
                    mutableLiveData.postValue(supportwdLocations);
                }


                };

            /**
             * get date  from Not supported location
             * @param e
             */
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
