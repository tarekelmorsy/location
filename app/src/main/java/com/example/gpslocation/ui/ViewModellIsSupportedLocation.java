package com.example.gpslocation.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gpslocation.data.Repository;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.BasketLocation;
import com.example.gpslocation.model.SupportwdLocation;
import com.example.gpslocation.model.SupportwdLocationDetails;


public class ViewModellIsSupportedLocation extends ViewModel {
    private MutableLiveData<String> country = new MutableLiveData<>();
    private MutableLiveData<Boolean> booleanIsSupported = new MutableLiveData<>();
    private MutableLiveData<SupportwdLocationDetails> supportwdLocationDetailsMutableLiveData =new MutableLiveData<>();




    public void setSupportwdLocationDetails(SupportwdLocationDetails supportwdLocationDetails ) {
        this.supportwdLocationDetailsMutableLiveData.setValue( supportwdLocationDetails);
    }

    public MutableLiveData<SupportwdLocationDetails> getSupportwdLocationDetails() {
        return supportwdLocationDetailsMutableLiveData;
    }


    public void setIsSupported(boolean isSupported ) {
        this.booleanIsSupported.setValue( isSupported);
    }

    public MutableLiveData<Boolean> getIsSupported() {
        return booleanIsSupported;
    }


    public void setCountry(String country) {
        this.country.setValue( country );
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    private Repository repository;



    public ViewModellIsSupportedLocation(){
        repository = Repository.getINSTANCEe();
        repository =Repository.getINSTANCEDetail();

    }

    public void  getretrofit(Double lat,Double lon){

        repository.getData(lat,lon);
    }
    public void getDetail(SupportwdLocationDetails supportwdLocationDetails){

        repository.fetchTransactiondetail(supportwdLocationDetails);
    }

    public MutableLiveData<SupportwdLocation> getMutableLiveDataSupportwdLocation(){
        return repository.getMutableLiveDataSupportwdLocation();


    }

    public MutableLiveData <ErrorResponse> getMutableLiveDataError(){
        return repository.getMutableLiveDataeror();
    }
    public MutableLiveData<BasketLocation> getBasketMutableLiveData(){

        return repository.basketMutableLiveData();
    }


}