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
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodordringapp.bottomBarFragment.RestuarantFragment;

import java.io.IOException;
import java.io.InputStream;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    String restaurantName[];
    String url[];
int rateC[],rateval[];
    Float totalRate[]=new Float[50];

    public Adapter(Context ct, String s1[],int rateC[],int rateval[], String img[]) {
        context = ct;
        this.restaurantName = s1;
        this.rateC=rateC;
        this.rateval=rateval;
        url = img;
        for (int i = 0; i < restaurantName.length; i++)//.length
        {
            totalRate[i] = ((rateval[i] * 1.0f) / rateC[i]);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.t1.setText(restaurantName[position]);
        String urllink = url[position];

        if (urllink.isEmpty()) {
            Toast.makeText(context.getApplicationContext(), "invalid loading",
                    Toast.LENGTH_LONG).show();
        } else {
            new LoadImage(holder.m1).execute(urllink); }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("name",restaurantName[position]);
                bundle.putFloat("totalrate",totalRate[position]);
                bundle.putString("image_path",url[position]);
                Fragment f = new RestuarantFragment();

                Fragment fragment = new RestaurantFragment2();
                fragment.setArguments(bundle);
                ((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                ((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction().hide(f);

            }
        });
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


    @Override
    public int getItemCount() {
        return 3;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        ImageView m1;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.CategoryView);
            m1 = itemView.findViewById(R.id.FoodView);
            cardView=itemView.findViewById(R.id.CardView);
        }
    }


}
