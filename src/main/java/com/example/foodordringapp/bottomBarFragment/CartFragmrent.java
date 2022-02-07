package com.example.foodordringapp.bottomBarFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.foodordringapp.R;


public class CartFragmrent extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,@Nullable Bundle savedInstanceState)
    {
     return inflater.inflate(R.layout.fragment_cart,container,false);


    }



}
