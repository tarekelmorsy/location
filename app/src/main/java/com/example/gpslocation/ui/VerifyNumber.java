package com.example.gpslocation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpslocation.R;
import com.example.gpslocation.model.CodeModel;
import com.example.gpslocation.model.Wrongcode;

public class VerifyNumber extends AppCompatActivity {
    Button btnext;
    EditText edInput1, edInput2, edInput3, edInput4,edInput5;
    TextView timer,code;
    public int counter=60;
    ViewModellIsSupportedLocation viewModellIsSupportedLocationll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);
        btnext   = findViewById(R.id.btnextname);
        edInput1 = findViewById(R.id.edInput1);
        edInput2 = findViewById(R.id.edInput2);
        edInput3 = findViewById(R.id.edInput3);
        edInput4 = findViewById(R.id.edInput4);
        edInput5 = findViewById(R.id.edInput5);
        timer    = findViewById(R.id.timer);
        code     = findViewById(R.id.code);
        viewModellIsSupportedLocationll = new ViewModelProvider(VerifyNumber.this).get(ViewModellIsSupportedLocation.class);

        setup();

        new CountDownTimer(60000, 1000){
            public void onTick(long millisUntilFinished){
                timer.setText("0:"+String.valueOf(counter));
                counter--;
            }
            public  void onFinish(){
                code.setText("");
                timer.setText("إعادة إرسال الكود ");
                timer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModellIsSupportedLocationll.getVerificationCode(getNumber());
                            counter = 60;
                            new CountDownTimer(60000, 1000){
                                public void onTick(long millisUntilFinished){
                                    counter--;
                                    code.setText("  يصل الكود خلال   ");
                                    timer.setText("0:"+String.valueOf(counter));
                                }
                                public  void onFinish(){
                                    code.setText("");
                                    timer.setText("إعادة إرسال الكود ");
                                }
                            }.start();}

                });
            }
        }.start();
        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nextInput(edInput1,edInput2);
        nextInput(edInput2,edInput3);
        nextInput(edInput3,edInput4);
        nextInput(edInput4,edInput5);


        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewModellIsSupportedLocationll.getVerificationCode(getNumber(),edInput1.getText().toString()+edInput2.getText().toString()+edInput3.getText().toString()+edInput4.getText().toString()+edInput5.getText().toString());

//966564989257
            }});



    }

    /**
     * as soon as user enters one number ,user will move  next EditText
     * @param edInput1
     * @param edInput2
     */
    public void nextInput(EditText edInput1, EditText edInput2 ){
        edInput1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (edInput1.getText().length()==1)
                    edInput2.requestFocus();
                return false;
            }
        });
    }
    /**
     * setup observe from view model
     */
    public void setup(){
        viewModellIsSupportedLocationll.getUserCodeMutableLiveDatae().observe(VerifyNumber.this, new Observer<CodeModel>() {

            @Override
            public void onChanged(CodeModel codeModel ) {

                if (codeModel.getResultCode().getHasFavoriteLocation()){
                    Intent intent = new Intent(VerifyNumber.this, MapsActivity.class);
                    startActivity(intent);
                    Toast.makeText(VerifyNumber.this, "نورت يا هندسه    "+codeModel.getResultCode().getUsername()+"   \uD83D\uDE05", Toast.LENGTH_LONG).show();

                }
                else {
                    Intent intent = new Intent(VerifyNumber.this, UserName.class);
                    startActivity(intent);


                }
            }
        });
        viewModellIsSupportedLocationll.getMutableLiveWrongCode().observe(this, new Observer<Wrongcode>() {
            @Override
            public void onChanged(Wrongcode wrongcode ) {
                Toast.makeText(VerifyNumber.this, wrongcode.getMessageAr(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * get number from Phone_number Activity
     * @return
     */
    public String getNumber(){

        Intent intent = getIntent();
        String phone_number = intent.getStringExtra("Phone_number");
        return phone_number;
    }

}
