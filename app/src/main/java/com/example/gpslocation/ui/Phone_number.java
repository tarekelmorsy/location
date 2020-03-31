package com.example.gpslocation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpslocation.R;
import com.example.gpslocation.model.UserPhoneModel;
import com.example.gpslocation.model.Wrongcode;

public class Phone_number extends AppCompatActivity {
    Button btnext;
    EditText inputNumber;
    String userNumber;
     ViewModellIsSupportedLocation viewModellIsSupportedLocationll;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        inputNumber= findViewById(R.id.inputNumber);
        btnext= findViewById(R.id.btnextnumber);
        viewModellIsSupportedLocationll = new ViewModelProvider(Phone_number.this).get(ViewModellIsSupportedLocation.class);
        setupObserve();

        btnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userNumber =inputNumber.getText().toString();
                viewModellIsSupportedLocationll.setUserPhone(userNumber);
                viewModellIsSupportedLocationll.getVerificationCode(viewModellIsSupportedLocationll.getUserPhone().getValue());

                //966564989257
                }


        });


        viewModellIsSupportedLocationll.setUserPhone(userNumber);


    }

    /**
     * setup observe from view model
     */
    public void setupObserve(){

        viewModellIsSupportedLocationll.getUserPhoneMutableLiveDatae().observe(Phone_number.this, new Observer<UserPhoneModel>() {

            @Override
            public void onChanged(UserPhoneModel usersPhone) {
                if (usersPhone.getStatus()){
                Intent intent = new Intent( Phone_number.this, VerifyNumber.class);
                intent.putExtra("Phone_number", userNumber);
                startActivity(intent);

                }
            }
        });

        viewModellIsSupportedLocationll.getMutableLiveWrongcode().observe(this, new Observer<Wrongcode>() {
            @Override
            public void onChanged(Wrongcode wrongcode ) {
                Toast.makeText(Phone_number.this, wrongcode.getMessageAr(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}
