package com.example.gpslocation.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gpslocation.data.LocationRepository;
import com.example.gpslocation.data.UserRepository;
import com.example.gpslocation.model.BasketLocation;
import com.example.gpslocation.model.CodeModel;
import com.example.gpslocation.model.ErrorResponse;
import com.example.gpslocation.model.SupportwdLocation;
import com.example.gpslocation.model.SupportwdLocationDetails;
import com.example.gpslocation.model.UserPhoneModel;
import com.example.gpslocation.model.Wrongcode;


public class ViewModellIsSupportedLocation extends ViewModel {
    private MutableLiveData<String> country = new MutableLiveData<>();
    private MutableLiveData<String> userPhone = new MutableLiveData<>();
    private MutableLiveData<Boolean> booleanIsSupported = new MutableLiveData<>();
    private MutableLiveData<SupportwdLocationDetails> supportwdLocationDetailsMutableLiveData =new MutableLiveData<>();

    public MutableLiveData<String> getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone.setValue(userPhone);
       // return userPhone;
    }

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

    private LocationRepository locationRepository;
    private UserRepository userRepository ;


/////////////////////////////////////////////////////////////////////////

    public ViewModellIsSupportedLocation(){
        locationRepository = LocationRepository.getINSTANCEe();
        userRepository=UserRepository.getINSTANCEe();

    }


    public void  getretrofit(Double lat,Double lon){

        locationRepository.getSupportLocation(lat,lon);
    }
    public void getDetail(SupportwdLocationDetails supportwdLocationDetails){

        locationRepository.fetchTransactiondetail(supportwdLocationDetails);
    }
    public void getVerificationCode(String userPhone ){

        userRepository.VerificationCode(userPhone);
    }
    public void getVerificationCode(String userPhone ,String code ){

        userRepository.code(userPhone,code);
    }


    public MutableLiveData<SupportwdLocation> getMutableLiveDataSupportwdLocation(){
        return locationRepository.getMutableLiveDataSupportwdLocation();


    }
    public MutableLiveData<UserPhoneModel> getUserPhoneMutableLiveDatae(){
        return userRepository.getUserPhoneMutableLiveData();


    }
    public MutableLiveData<CodeModel> getUserCodeMutableLiveDatae(){
        return userRepository.getcodeMutableLiveData();


    }

    public MutableLiveData <ErrorResponse> getMutableLiveDataError(){
        return locationRepository.getMutableLiveDataeror();
    }

    public MutableLiveData <Wrongcode> getMutableLiveWrongcode(){
        return userRepository.getMutableLiveWrongNumber();
    }

    public MutableLiveData <Wrongcode> getMutableLiveWrongCode(){
        return userRepository.getMutableLiveWrongCode();
    }
    public MutableLiveData<BasketLocation> getBasketMutableLiveData(){

        return locationRepository.basketMutableLiveData();
    }


}