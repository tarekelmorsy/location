package com.example.gpslocation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpslocation.R;

public class Phone_number extends AppCompatActivity {
    Button btnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        btnext= findViewById(R.id.btnextnumber);
        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( Phone_number.this, ConfirmNumber.class);
                startActivity(intent);
            }
        });


    }
}
