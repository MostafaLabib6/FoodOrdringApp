package com.example.foodordringapp;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordringapp.bottomBarFragment.RestuarantFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import food.ordering.database.DatabaseAdapter;
import food.ordering.database.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private FragmentActivity myContext;
    Context context;
    String name[];
    int rateCount[];
    int rateValue[];
    float totalRate[] = new float[50];
    String restaurantImage[];

    public RestaurantAdapter(Context context, String name[], int rateCount[], int rateValue[], String restaurantImage[]) {
        this.context = context;
        this.name = name;
        this.rateCount = rateCount;
        this.rateValue = rateValue;
        this.restaurantImage = restaurantImage;
        for (int i = 0; i < name.length; i++)//.length
        {
            totalRate[i] = ((rateValue[i] * 1.0f) / rateCount[i]);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.showrestaurant, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.restaurantName.setText("name : " + name[position]);
        holder.ratingBar.setRating(20);
        holder.ratingBar.setEnabled(false);
        holder.ratingText.setText(String.valueOf(totalRate[position]));

        new LoadImage(holder.restaurant_Image).execute(restaurantImage[position]);
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("name",name[position]);
                bundle.putFloat("totalrate",totalRate[position]);
                bundle.putString("image_path",restaurantImage[position]);
                Fragment f = new RestuarantFragment();

                Fragment fragment = new RestaurantFragment2();
                fragment.setArguments(bundle);
                ((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                ((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction().hide(f);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        ImageView restaurant_Image;
        RatingBar ratingBar;
        ConstraintLayout mainlayout;
        TextView ratingText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurant_Image = itemView.findViewById(R.id.resturantImage1);
            restaurantName = itemView.findViewById(R.id.nameTextView1);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ratingBar.setEnabled(false);
            mainlayout = itemView.findViewById(R.id.co);//////////////////
            ratingText=itemView.findViewById(R.id.ratingTextView1);
        }
    }

    public void setRestaurantData() {
        Restaurant restaurant;
        DatabaseAdapter databaseAdapter;
        for (int j = 0; j < 5; j++) {
            String restaurantName = "", restaurantImage = " ";
            int rateCountRandom, rateValueRandom;
            Random random = new Random(System.currentTimeMillis());
            rateCountRandom = random.nextInt(15) + 1;
            rateValueRandom = random.nextInt(75) + 15;
            for (int i = 0; i < 6; i++) {
                int name = random.nextInt(25);
                char c = (char) name;
                restaurantName += name;
            }
            restaurant = new Restaurant(restaurantName, rateCountRandom, rateValueRandom, restaurantImage);
            // databaseAdapter.insert(restaurant);
        }
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView image) {
            this.imageView = image;
            imageView.findViewById(R.id.resturantImage1);
        }


        @Override
        protected Bitmap doInBackground(String... strings) {
            String urllink = strings[0];
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