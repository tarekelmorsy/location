package com.example.gpslocation.ui;
import android.content.Intent;
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

public class DialogIsSupported extends androidx.fragment.app.DialogFragment {

    Button again, btGo;
    TextView tvSupported, tvCountry;
    ImageView imageView;
     ConstraintLayout frameLayout;
    ViewModellIsSupportedLocation viewModellIsSupportedLocation =  new ViewModellIsSupportedLocation();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModellIsSupportedLocation = new ViewModelProvider(getActivity()).get(ViewModellIsSupportedLocation.class);

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

        if (viewModellIsSupportedLocation.getIsSupported().getValue()) {

            tvSupported.setText(R.string.SupportedLocation);
            tvCountry.setText( viewModellIsSupportedLocation.getCountry().getValue());
            imageView.setImageResource(R.drawable.imagsuccessful_foreground);
            again.setText(R.string.TheLocationChanged);
            btGo.setText(R.string.goSary);



            btGo.setOnClickListener(v -> {

                viewModellIsSupportedLocation.getDetail(viewModellIsSupportedLocation.getSupportwdLocationDetails().getValue());
                viewModellIsSupportedLocation.getBasketMutableLiveData().observe((LifecycleOwner) getContext(), new Observer<BasketLocation>() {
                    @Override
                    public void onChanged(BasketLocation basketLocation) {


                    }
                });

            });


        } else {
            tvSupported.setText(R.string.notSupportedLocation );
            imageView.setImageResource(R.drawable.imagunsuccessful_foreground);
            again.setText(R.string.anotherLocation);
            btGo.setText(R.string.introduceYourself);
            tvCountry.setText( viewModellIsSupportedLocation.getCountry().getValue());
            btGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(),Phone_number.class);
                    startActivity(intent);
                }
            });



        }


    }

}