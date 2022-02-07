package com.example.foodordringapp.bottomBarFragment;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordringapp.R;
import com.example.foodordringapp.RestaurantAdapter;

import food.ordering.database.DatabaseAdapter;
import food.ordering.database.Restaurant;

public class RestuarantFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,@Nullable Bundle savedInstanceState)
    {
        RecyclerView recyclerView;
        String getRestaurantData[][]=new String[50][4],restaurantName[]=new String[50],restaurantImage[]=new String[50];
        int rateValue[]=new int[50],rateCount[]=new int[50];
        String URL1[]={"https://image.shutterstock.com/image-photo/healthy-food-clean-eating-selection-260nw-722718082.jpg","https://image.shutterstock.com/image-photo/healthy-food-clean-eating-selection-260nw-722718082.jpg"};
            Restaurant restaurant;
        View view= inflater.inflate(R.layout.fragment_resturants,container,false);
        recyclerView = view.findViewById(R.id.recyclerviewrest);
       DatabaseAdapter databaseAdapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            databaseAdapter = new DatabaseAdapter(getContext());
        }
        Restaurant restaurants[] = databaseAdapter.getRestaurants();
        for(int i=0;i<restaurants.length;i++) {
            getRestaurantData[i]= restaurants[i].getValues();
        }
        for(int i=0;i<restaurants.length;i++) {
            restaurantName[i]=getRestaurantData[i][0];
            rateCount[i]=Integer.parseInt(getRestaurantData[i][1]);
            rateValue[i]=Integer.parseInt(getRestaurantData[i][2]);
            restaurantImage[i]=getRestaurantData[i][3];
        }

        RestaurantAdapter RestaurantAdapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RestaurantAdapter = new RestaurantAdapter(getContext(),restaurantName,rateCount,rateValue,restaurantImage);
        }
        recyclerView.setAdapter(RestaurantAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }


        return view;

    }
    }






