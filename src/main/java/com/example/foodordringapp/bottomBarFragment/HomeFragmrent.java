package com.example.foodordringapp.bottomBarFragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordringapp.Adapter;
import com.example.foodordringapp.Burger;
import com.example.foodordringapp.Dessert;
import com.example.foodordringapp.Drinks;
import com.example.foodordringapp.Pizza;
import com.example.foodordringapp.R;

import food.ordering.database.DatabaseAdapter;
import food.ordering.database.Restaurant;


public class HomeFragmrent extends Fragment {
    RecyclerView recyclerView;


    String getRestaurantData[][]=new String[50][4],restaurantName[]=new String[50], imagePath[]=new String[50];
    int ratecounter[]=new int[50];
    int ratevalue[]=new int [50];
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* Consumable c1=new Consumable("Burger king","Buffalo Burger","Burger",135.99,true,"https://www.kindpng.com/picc/m/105-1058739_buffalo-burger-menu-hd-png-download.png",0);
        Consumable c2=new Consumable("Burger double","Buffalo Burger","Burger",99,true,"https://www.kindpng.com/picc/m/70-706025_buffalo-burger-hd-png-download.png",0);
        Consumable c3=new Consumable("cinnabon","Buffalo Burger","Burger",147.99,false,"https://img.favpng.com/20/24/22/cinnamon-roll-cinnabon-bakery-coffee-png-favpng-J2gXnc0arrc3SDXdVhvhujPQT.jpg",0);
        Consumable c4=new Consumable("7up","Buffalo Burger","7UP",9.99,false,"https://toppng.com//public/uploads/preview/7up-can-11538598609dqjkdandfy.png",0);


DatabaseAdapter dp=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            dp = new DatabaseAdapter(getContext());
        }
        dp.insert(c1);
        dp.insert(c2);
        dp.insert(c3);
        dp.insert(c4);*/

        DatabaseAdapter databaseAdapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            databaseAdapter = new DatabaseAdapter(getContext());
        }
        Restaurant restaurants[] = databaseAdapter.getRestaurants();
        for(int i=0;i<restaurants.length;i++) {
            getRestaurantData[i]= restaurants[i].getValues();
            //get values returns  name ,rate ,image path
        }
        for(int i=0;i<restaurants.length;i++){
            restaurantName[i]= getRestaurantData[i][0];
            ratecounter[i]=Integer.parseInt(getRestaurantData[i][1]);
            ratevalue[i]=Integer.parseInt(getRestaurantData[i][2]);
            imagePath[i]= getRestaurantData[i][3];
        }



        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //
        recyclerView = view.findViewById(R.id.recyclerView);
        Adapter adapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adapter = new Adapter(getContext(), restaurantName,ratecounter,ratevalue ,imagePath);
        }
        LinearLayoutManager linearLayoutManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            linearLayoutManager = new
                    LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        Button btn_1=(Button) view.findViewById(R.id.explore_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),Burger_activity.class));
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container,new Burger()).addToBackStack(null).commit();

                //Toast.makeText(getBaseContext(), "Some Burgers!!", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_2=(Button) view.findViewById(R.id.explore_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container,new Pizza()).addToBackStack(null).commit();

                // startActivity(new Intent(getApplicationContext(),Pizza_activity.class));
            }
        });
        Button btn_3=(Button) view.findViewById(R.id.explore_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container,new Drinks()).addToBackStack(null).commit();

                // startActivity(new Intent(getApplicationContext(),Drinks_activity.class));
            }
        });
        Button btn_4=(Button) view.findViewById(R.id.explore_4);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container,new Dessert()).addToBackStack(null).commit();

                // startActivity(new Intent(getApplicationContext(),Dessert_activity.class));
            }
        });

        return view;
    }


}
