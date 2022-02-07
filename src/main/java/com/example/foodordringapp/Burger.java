package com.example.foodordringapp;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class Burger extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,@Nullable Bundle savedInstanceState)
    {

        View view =inflater.inflate(R.layout.activity_burger,container,false);
        Button b1=(Button) view.findViewById(R.id.btn_add1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(getContext(), "ADDED", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button b2=(Button) view.findViewById(R.id.btn_add2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(getContext(), "ADDED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button b3=(Button) view.findViewById(R.id.btn_add3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(getContext(), "ADDED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button b4=(Button) view.findViewById(R.id.btn_add4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(getContext(), "ADDED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button b5=(Button) view.findViewById(R.id.btn_add5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(getContext(), "ADDED", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }



}
