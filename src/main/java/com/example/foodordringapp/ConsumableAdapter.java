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

public class ConsumableAdapter extends RecyclerView.Adapter<ConsumableAdapter.MyViewHolder>{
    Context context;
    String name[];
    double consumablePrice[];
    String consumableImage[];
    public ConsumableAdapter(Context context, String[] name, double[] consumablePrice, String[] consumableImage) {
        this.context = context;
        this.name = name;
        this.consumablePrice = consumablePrice;
        this.consumableImage = consumableImage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.categoryofrestaurant,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.consumableName.setText(name[position]);
        holder.consumablePrice.setText("Price : "+String.valueOf(consumablePrice[position]));
        new LoadImage(holder.consumableImage).execute(consumableImage[position]);
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("name",name[position]);
                bundle.putDouble("price",consumablePrice[position]);
                bundle.putString("image_path",consumableImage[position]);
                Fragment f = new RestuarantFragment();
                Fragment fragment = new RestaurantFragment3();
                fragment.setArguments(bundle);
                ((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,fragment ).addToBackStack(null).commit();
                ((FragmentActivity) v.getContext()).getFragmentManager().beginTransaction().hide(f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView consumableName;
        TextView consumablePrice;
        ImageView consumableImage;
        ConstraintLayout mainlayout;
        RatingBar rate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            consumableImage=itemView.findViewById(R.id.category);
            consumableName=itemView.findViewById(R.id.CategoryName45);
            consumablePrice=itemView.findViewById(R.id.categoryPrice1223);
            mainlayout=itemView.findViewById(R.id.con);
            rate=itemView.findViewById(R.id.ratingBar3);
        }
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
