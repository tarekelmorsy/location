package com.example.gpslocation.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gpslocation.data.Repository;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.BasketLocation;
import com.example.gpslocation.model.SupportwdLocation;
import com.example.gpslocation.model.SupportwdLocationDetails;


public class ViewModell extends ViewModel {
    private MutableLiveData<SupportwdLocationDetails> basketMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> country = new MutableLiveData<>();
    private MutableLiveData<String> supported = new MutableLiveData<>();
    private MutableLiveData<Boolean> booleanIsSupported = new MutableLiveData<>();
    private MutableLiveData<SupportwdLocationDetails> supportwdLocationDetailsMutableLiveData =new MutableLiveData<>();




    public void setSupportwdLocationDetails(SupportwdLocationDetails supportwdLocationDetails ) {
        this.supportwdLocationDetailsMutableLiveData.setValue( supportwdLocationDetails);
    }

    public MutableLiveData<SupportwdLocationDetails> getSupportwdLocationDetails() {
        return supportwdLocationDetailsMutableLiveData;
    }


    public void setBoolean(boolean b ) {
        this.booleanIsSupported.setValue( b);
    }

    public MutableLiveData<Boolean> getBoolean() {
        return booleanIsSupported;
    }


    public void setCountry(String stringd) {
        this.country.setValue( stringd );
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    public void setSupported(String stringd) {
        this.supported.setValue( stringd );
    }

    public MutableLiveData<String> getSupported() {
        return supported;
    }

    private Repository repository;



    public SupportwdLocationDetails getbasketMutableLiveData(){
        return basketMutableLiveData.getValue();
    }

    public ViewModell (){
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