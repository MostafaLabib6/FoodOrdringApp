package com.example.foodordringapp.bottomBarFragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordringapp.FavoritesAdapter;
import com.example.foodordringapp.R;

import food.ordering.database.DatabaseAdapter;


public class FavouritsFragmrent extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {



    DatabaseAdapter databaseAdapter;


    String message;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,@Nullable Bundle savedInstanceState)
    {
     View view= inflater.inflate(R.layout.fragment_fav,container,false);
        DatabaseAdapter databaseAdapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            databaseAdapter = new DatabaseAdapter(getContext());
        }

       // requestPermission();
        RecyclerView favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
//        favoritesRecyclerView.getLayoutManager().offsetChildrenVertical(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            favoritesRecyclerView.setAdapter(new FavoritesAdapter(getContext(), databaseAdapter,
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .getAbsolutePath()));
        }
//



        return view;
    }



}
