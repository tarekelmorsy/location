package com.example.gpslocation.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpslocation.R;

public class Test extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = findViewById(R.id.txttest);
        Bundle bundle1= new Bundle();

       // Bundle bundle = getArguments();
      textView.setText(  bundle1.getBundle("ppp").getString(" userr"));

    }
}
