package com.example.gpslocation.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gpslocation.R;

public class DialogFragment     extends androidx.fragment.app.DialogFragment {

    Button button1, button2;
    TextView textView1, textView2;
    ImageView imageView;
    FrameLayout frameLayout;

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
        frameLayout = view.findViewById(R.id.llll);
        textView1 = view.findViewById(R.id.txissuccessful);
        textView2 = view.findViewById(R.id.location);
        imageView = view.findViewById(R.id.igIcon);
// getDialog().setCanceledOnTouchOutside(true);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
               // getDialog().setCanceledOnTouchOutside(true);

            }
        });

        Bundle bundle = getArguments();
        if (bundle.getBoolean("Status") == true) {


            textView2.setText(bundle.getString("user"));
            textView1.setText(bundle.getString("place"));
            imageView.setImageResource(R.drawable.imagsuccessful_foreground);
            button1.setText("لا موقع ثاني");
            button2.setText("يلا سارينا");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


        } else {

            textView2.setText(bundle.getString("user"));
            textView1.setText(bundle.getString("place"));
            imageView.setImageResource(R.drawable.imagunsuccessful_foreground);
            button1.setText("غير موقعك");
            button2.setText("عرفنا عليك");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


        }


    }


}
