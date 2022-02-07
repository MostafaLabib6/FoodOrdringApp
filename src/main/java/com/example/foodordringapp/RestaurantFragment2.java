package com.example.foodordringapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;

import food.ordering.database.Consumable;
import food.ordering.database.DatabaseAdapter;


public class RestaurantFragment2 extends Fragment {
    ImageView restaurantImage;
    TextView restaurantName, totalRating;
    String restaurantNameString, restaurantImageString;
    float totalRatingFloat;
    RecyclerView recyclerView;
    Consumable allConsumable[]=new Consumable[50];
    String consumableName[] = new String[50], consumableImage[] = new String[50], consumableNameArray[] = new String[50], consumableImageArray[] = new String[50];
    float rateValue[] = new float[50];
    boolean offer;
    double consumablePriceArray[] = new double[50];
    RatingBar ratingBar;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);
        restaurantImage = view.findViewById(R.id.imageView3);
        restaurantName = view.findViewById(R.id.restuarntname);
        ratingBar = view.findViewById(R.id.ratingBar3);
        ratingBar.setRating(50);
        ratingBar.setEnabled(false);
        //ratingBar.setEnabled(false);
        totalRating=view.findViewById(R.id.ratingofrestaurant);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getData();
        }
        setData();
        DatabaseAdapter adapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adapter = new DatabaseAdapter(getContext());
        }
        Consumable consumable[] = adapter.getConsumables(restaurantNameString, null,offer);
       /* Consumable consumable1[] = adapter.getConsumables(restaurantNameString, null,offer);
        Consumable consumable2[] = adapter.getConsumables(restaurantNameString, null,offer);
        Consumable consumable3[] = adapter.getConsumables(restaurantNameString, null,offer);*/

        for (int i = 0; i < consumable.length; i++) {
            allConsumable[i] = consumable[i];
        }
       /* for (int i = consumable.length; i < consumable1.length + consumable.length; i++) {
            allConsumable[i] = consumable1[i];
        }
        for (int i = consumable1.length + consumable.length; i < consumable.length + consumable1.length + consumable2.length; i++) {
            allConsumable[i] = consumable2[i];
        }
        for (int i = consumable1.length + consumable.length+consumable2.length;
             i < consumable.length + consumable1.length + consumable2.length+consumable3.length; i++) {
            allConsumable[i] = consumable3[i];
        }*/
        for (int i = 0; i < consumable.length ; i++)/////////////////.length
        {
            consumableNameArray[i] = allConsumable[i].consumableName;
            consumableImageArray[i] = allConsumable[i].imagePath;
            consumablePriceArray[i] = allConsumable[i].price;
        }
        recyclerView = view.findViewById(R.id.re);
        ConsumableAdapter consumableAdapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            consumableAdapter = new ConsumableAdapter(getContext(), consumableNameArray, consumablePriceArray, consumableImageArray);
        }
        recyclerView.setAdapter(consumableAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            restaurantNameString=bundle.getString("name");
            totalRatingFloat= bundle.getFloat("totalrate",2);
            restaurantImageString= bundle.getString("image_path");


        }
        }


    public void setData() {
        restaurantName.setText("name : " + restaurantNameString);
        new LoadImage(restaurantImage).execute(restaurantImageString);
        totalRating.setText("X"+String.valueOf(totalRatingFloat));
    }
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView image) {
            this.imageView = image;
            imageView.findViewById(R.id.imageView3);
        }


        @Override
        protected Bitmap doInBackground(String... strings) {
            String urllink =strings[0];
            Bitmap bitmap = null;
            try {
                try (InputStream inputStream = new java.net.URL(urllink).openStream()) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;

        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }



}
