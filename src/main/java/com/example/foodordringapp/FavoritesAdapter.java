package com.example.foodordringapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;

import food.ordering.database.Adapter2;
import food.ordering.database.Consumable;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView consumableImage;
        public TextView consumableName;
        public TextView restaurantName;
        public Switch favoriteSwitch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            consumableImage = itemView.findViewById(R.id.consumableImage);
            consumableName = itemView.findViewById(R.id.consumableName);
            restaurantName = itemView.findViewById(R.id.retaurantName);
            favoriteSwitch = itemView.findViewById(R.id.favoriteSwitch);
        }
    }

    private Adapter2 adapter;
    private Context context;
    private Consumable[] consumables;
    private String consumablesImagesRootDirectory;

    public FavoritesAdapter(Context context, Adapter2 adapter,
                            String consumablesImagesRootDirectory) {
        this.adapter = adapter;
        this.context = context;
        consumables = adapter.getFavorites();
        this.consumablesImagesRootDirectory = consumablesImagesRootDirectory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.consumableName.setText(consumables[position].consumableName);
        holder.restaurantName.setText(consumables[position].restaurantName);
        holder.favoriteSwitch.setChecked(consumables[position].favorite);
        new LoadImage(holder.consumableImage).execute(consumables[position].imagePath);
        /*holder.consumableImage.setImageBitmap(BitmapFactory.decodeFile(
                consumablesImagesRootDirectory + "/" + consumables[position].imagePath));*/
        holder.favoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adapter.setFavorite(consumables[position].consumableName,
                        consumables[position].restaurantName, b);
            }
        });
        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return consumables.length;
    }
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView image) {
            this.imageView = image;
            imageView.findViewById(R.id.consumableImage);
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
