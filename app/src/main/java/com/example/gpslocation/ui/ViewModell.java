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