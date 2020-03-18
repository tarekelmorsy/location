package com.example.gpslocation.ui;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.gpslocation.R;
import com.example.gpslocation.model.BasketLocation;

public class DialogFragmenttt extends androidx.fragment.app.DialogFragment {

    Button button1, button2;
    TextView textView1, textView2;
    ImageView imageView;
     ConstraintLayout frameLayout;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    ViewModell viewModell =  new ViewModell();


    @Nullable


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialogfragment, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        button1 = view.findViewById(R.id.btsecondlocation);
        button2 = view.findViewById(R.id.btgo);
        button1.setOnClickListener(v -> dismiss());
        frameLayout = view.findViewById(R.id.ll);
        textView1 = view.findViewById(R.id.txissuccessful);
        textView2 = view.findViewById(R.id.location);
        imageView = view.findViewById(R.id.igIcon);
        frameLayout.setOnClickListener(v -> dismiss());




        Bundle bundle = getArguments();
        if (bundle.getBoolean("Status") == true) {


            textView2.setText(bundle.getString("user"));
            viewModell.getString().observe((LifecycleOwner) getContext(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                  //  textView1.setText(s);
                    textView2.setText(s);

                }
            });
         //   textView1.setText(bundle.getString("place"));
            imageView.setImageResource(R.drawable.imagsuccessful_foreground);
            button1.setText("لا موقع ثاني");
            button2.setText("يلا سارينا");


            button2.setOnClickListener(v -> {
              //  Log.d("ooooo" , bundle.getString("userr"));
              //  MapsActivity.checkLocationSupport=true;
              viewModell.getDetail(MapsActivity.getSupportwdLocationDetails() );
               viewModell.getDetail(viewModell.getbasketMutableLiveData());
            //    Log.d("pppppppp",viewModell.getbasketMutableLiveData().getDefault().toString());
                viewModell.getBasketMutableLiveData().observe((LifecycleOwner) getContext(), new Observer<BasketLocation>() {
                    @Override
                    public void onChanged(BasketLocation basketLocation) {
                       // Log.d("pppppppp",isSupportwdLocation.getMessage());
                       // Log.d("ll;;;",isSupportwdLocation.toString());



                    }
                });

               // MapsActivity.getSupportedLocationDetails(MapsActivity.supportwdLocationDetails);
              //  Intent intent = new Intent(getContext(),Phone.class);
                //startActivity(intent);

                /*
                MapsActivity.checkLocationSupport=true;


                MapsActivity.viewModell.getIsSupportwdLocationMutableLiveData().observe((LifecycleOwner) getContext(), new Observer<IsSupportwdLocation>() {
                    @Override
                    public void onChanged(IsSupportwdLocation isSupportwdLocation) {


                        Log.d("pppppppp",isSupportwdLocation.getMessage());

                    }
                });
                Intent intent = new Intent(getContext(),Phone.class);
                startActivity(intent);



            }*/
            });


        } else {

            textView2.setText(bundle.getString("user"));
            textView1.setText(viewModell.getString().getValue());

           // textView1.setText(bundle.getString("place"));
            imageView.setImageResource(R.drawable.imagunsuccessful_foreground);
            button1.setText("غير موقعك");
            button2.setText("عرفنا عليك");
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(),Phone.class);
                    startActivity(intent);
                }
            });



        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*
        MapsActivity.viewModell.getIsSupportwdLocationMutableLiveData().observe(getViewLifecycleOwner(), new Observer<IsSupportwdLocation>() {
            @Override
            public void onChanged(IsSupportwdLocation isSupportwdLocation) {
                Log.d("pppppppp",isSupportwdLocation.getMessage());

            }
        });
*/
    }
}