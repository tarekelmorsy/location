package com.example.gpslocation.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gpslocation.data.Repository;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.SupportwdLocation;


public class ViewModell extends ViewModel {




    private Repository repository;


    public ViewModell (){
        repository = Repository.getINSTANCEe();

    }

    public void  getretrofit(Double lat,Double lon){

        repository.getData(lat,lon);
    }

    public MutableLiveData<SupportwdLocation> getMutableLiveDataSupportwdLocation(){
        return repository.getMutableLiveDataSupportwdLocation();


    }

    public MutableLiveData <ErrorResponse> getMutableLiveDataError(){
        return repository.getMutableLiveDataeror();


    }

}