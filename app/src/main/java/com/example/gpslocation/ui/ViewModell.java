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
    private MutableLiveData<String> string = new MutableLiveData<>();
    private MutableLiveData<String> string2 = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuppotted = new MutableLiveData<>();
    private MutableLiveData<SupportwdLocationDetails> supportwdLocationDetailsMutableLiveData =new MutableLiveData<>();




    public void setSupportwdLocationDetails(SupportwdLocationDetails supportwdLocationDetails ) {
        this.supportwdLocationDetailsMutableLiveData.setValue( supportwdLocationDetails);
    }

    public MutableLiveData<SupportwdLocationDetails> getSupportwdLocationDetails() {
        return supportwdLocationDetailsMutableLiveData;
    }


    public void setBoolean(boolean b ) {
        this.isSuppotted.setValue( b);
    }

    public MutableLiveData<Boolean> getBoolean() {
        return isSuppotted;
    }


    public void setString(String stringd) {
        this.string.setValue( stringd );
    }

    public MutableLiveData<String> getString() {
        return string;
    }

    public void setString2(String stringd) {
        this.string2.setValue( stringd );
    }

    public MutableLiveData<String> getString2() {
        return string2;
    }

    private Repository repository;



    public void setBasketMutableLiveData( SupportwdLocationDetails basketMutableLiveDatae) {

        basketMutableLiveData.setValue(basketMutableLiveDatae);
    }
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