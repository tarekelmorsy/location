package com.example.gpslocation.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gpslocation.data.Repository;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.IsSupportwdLocation;
import com.example.gpslocation.model.SupportwdLocation;
import com.example.gpslocation.model.SupportwdLocationDetails;


public class ViewModell extends ViewModel {




    private Repository repository;


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
    public MutableLiveData<IsSupportwdLocation> getIsSupportwdLocationMutableLiveData(){

        return repository.isSupportwdLocationMutableLiveData();
    }


}