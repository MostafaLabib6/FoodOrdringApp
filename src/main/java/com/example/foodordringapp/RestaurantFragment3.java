package com.example.foodordringapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.foodordringapp.bottomBarFragment.CartFragmrent;

import java.io.IOException;
import java.io.InputStream;


public class RestaurantFragment3 extends Fragment {
    TextView consumableName,consumablePrice;
    ImageView consumableImage;
    Button buy;
    String consumableNameString,consumableImageString;
    double consumablePriceDouble;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,@Nullable Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.activity_main3,container,false);
        consumableName =view.findViewById(R.id.categoryName);
        consumablePrice =view.findViewById(R.id.categoryprice);
        consumableImage =view.findViewById(R.id.categoeyImage);
        buy=view.findViewById(R.id.buyButton);
        buy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity().getApplicationContext(), "you buy it and you will get it at ",Toast.LENGTH_SHORT).show();
                //finish();

                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container,new CartFragmrent()).addToBackStack(null).commit();
                //why it error
            }
        });
        getData();
        setData();
        return view;
    }

        @SuppressLint("NewApi")
        public void getData() {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                consumableNameString=bundle.getString("name");
                consumableImageString=bundle.getString("image_path");
                consumablePriceDouble=bundle.getDouble("price",3);
            }

          }
        public void setData()
        {
            consumableName.setText("name : " + consumableNameString);
            consumablePrice.setText("prize : "+String.valueOf(consumablePriceDouble));
            new LoadImage(consumableImage).execute(consumableImageString);
            ///image;
        }
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView image) {
            this.imageView = image;
            imageView.findViewById(R.id.FoodView);
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
