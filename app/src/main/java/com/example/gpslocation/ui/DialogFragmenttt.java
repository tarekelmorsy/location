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

    Button again, btGo;
    TextView tvSupported, tvCountry;
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
        again = view.findViewById(R.id.btsecondlocation);
        btGo = view.findViewById(R.id.btgo);
        again.setOnClickListener(v -> dismiss());
        frameLayout = view.findViewById(R.id.ll);
        tvSupported = view.findViewById(R.id.txissuccessful);
        tvCountry = view.findViewById(R.id.location);
        imageView = view.findViewById(R.id.igIcon);
        frameLayout.setOnClickListener(v -> dismiss());




        Bundle bundle = getArguments();
        if (bundle.getBoolean("Status") == true) {


            tvCountry.setText(bundle.getString("country"));

            tvSupported.setText(bundle.getString("supported"));
            imageView.setImageResource(R.drawable.imagsuccessful_foreground);
            again.setText("لا موقع ثاني");
            btGo.setText("يلا سارينا");


            btGo.setOnClickListener(v -> {
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


            });


        } else {

            tvCountry.setText(bundle.getString("country"));

           tvSupported.setText(bundle.getString("supported"));
            imageView.setImageResource(R.drawable.imagunsuccessful_foreground);
            again.setText("غير موقعك");
            btGo.setText("عرفنا عليك");
            btGo.setOnClickListener(new View.OnClickListener() {
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