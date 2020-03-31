package com.example.gpslocation.data;

import androidx.lifecycle.MutableLiveData;

import com.example.gpslocation.model.CodeModel;
import com.example.gpslocation.model.UserPhoneModel;
import com.example.gpslocation.model.Wrongcode;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UserRepository {



    private MutableLiveData<UserPhoneModel> userPhoneMutableLiveData =new MutableLiveData<>();
    private MutableLiveData<CodeModel> codeMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Wrongcode> errorNumberMutableLiveData =new MutableLiveData<>();
    private MutableLiveData<Wrongcode> errorCodeMutableLiveData =new MutableLiveData<>();





    private static UserRepository INSTANCE;

    private ApiInterface apiInterface;



    public static UserRepository getINSTANCEe() {
        if (null == INSTANCE){
            INSTANCE = new UserRepository();

        }


        return INSTANCE;
    }




    private UserRepository() {

        Retrofit retrofit = Client.getINSTANCE();
        apiInterface= retrofit.create(ApiInterface.class);
    }






    public MutableLiveData <CodeModel> getcodeMutableLiveData(){
        return codeMutableLiveData;
    }

    public MutableLiveData <UserPhoneModel> getUserPhoneMutableLiveData(){
        return userPhoneMutableLiveData;
    }
    public MutableLiveData <Wrongcode> getMutableLiveWrongNumber(){

        return errorNumberMutableLiveData;
    }
    public MutableLiveData <Wrongcode> getMutableLiveWrongCode(){

        return errorCodeMutableLiveData;
    }

    /**
     * Confirm the phone number by sending the code
     * @param phone
     * @param code
     */

    public void code(String phone,String code ){

        Observable observable= apiInterface.loginbycode( phone,code )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<CodeModel> codeModelObserver = new Observer<CodeModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CodeModel codeModel) {
                codeMutableLiveData.postValue(codeModel);

            }

            /**
             * the user Entered a wrong code
             * @param e
             */
            @Override
            public void onError(Throwable e) {

                if (e instanceof RetrofitException) {
                    try {
                        RetrofitException retrofitException = (RetrofitException) e;
                        Wrongcode wrongcode  = retrofitException.getErrorBodyAs(Wrongcode.class);
                        errorCodeMutableLiveData.postValue(wrongcode);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(codeModelObserver);

    }

    /**
     * Send the verification code to the user's phone
     * @param UserPhone
     */
    public void VerificationCode(final String UserPhone) {
        Observable observable = apiInterface.gitUserCode(UserPhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
        Observer<UserPhoneModel> observer = new Observer<UserPhoneModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserPhoneModel userPhone) {

                userPhoneMutableLiveData.postValue(userPhone);

            }

            /**
             * the user Entered a wrong number
             * @param e
             */
            @Override
            public void onError(Throwable e) {
                if (e instanceof RetrofitException) {
                    try {
                        RetrofitException retrofitException = (RetrofitException) e;
                        Wrongcode wrongcode  = retrofitException.getErrorBodyAs(Wrongcode.class);
                        errorNumberMutableLiveData.postValue(wrongcode);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }


            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }



}
