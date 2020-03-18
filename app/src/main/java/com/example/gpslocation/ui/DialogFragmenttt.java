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
import androidx.lifecycle.ViewModelProvider;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       // viewModell= ViewModelProviders.of(getActivity()).get(ViewModell.class);
        viewModell = new ViewModelProvider(getActivity()).get(ViewModell.class);

        super.onCreate(savedInstanceState);
       // viewModell = new ViewModelProvider(this).get(ViewModell.class);

    }

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
        if (viewModell.getBoolean().getValue()) {

             // textView1.setText(bundle.getString("place"));
            tvSupported.setText(viewModell.getSupported().getValue());
            tvCountry.setText( viewModell.getCountry().getValue());

          //  textView2.setText(bundle.getString("user"));


         //   textView1.setText(bundle.getString("place"));
            imageView.setImageResource(R.drawable.imagsuccessful_foreground);
            again.setText("لا موقع ثاني");
            btGo.setText("يلا سارينا");


            btGo.setOnClickListener(v -> {
              //  Log.d("ooooo" , bundle.getString("userr"));
              //  MapsActivity.checkLocationSupport=true;
              viewModell.getDetail(viewModell.getSupportwdLocationDetails().getValue());
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

           // textView2.setText(bundle.getString("user"));
           // textView1.setText(viewModell.getString().getValue());

            tvSupported.setText(viewModell.getSupported().getValue());
            imageView.setImageResource(R.drawable.imagunsuccessful_foreground);
            again.setText("غير موقعك");
            btGo.setText("عرفنا عليك");

            tvCountry.setText( viewModell.getCountry().getValue());

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