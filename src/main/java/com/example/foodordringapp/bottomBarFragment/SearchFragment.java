package com.example.foodordringapp.bottomBarFragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.foodordringapp.R;
import com.example.foodordringapp.RestaurantFragment2;

import java.util.ArrayList;

import food.ordering.database.DatabaseAdapter;
import food.ordering.database.Restaurant;


public class SearchFragment extends Fragment {
     //getSupportActionBar().hide();
    ArrayAdapter<String> adapter;
    ArrayList<String> restaurant;
    ArrayList<String> restaurants_search;
    String RestaurantName,ImagePath;
    int Rate;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,@Nullable Bundle savedInstanceState)
    {
        int ratecounter[]=new int[50];
        int ratevalue[]=new int [50];

        String getRestaurantData[][]=new String[50][4],restaurantName[]=new String[50], imagePath[]=new String[50];
        Float total_rate[]=new Float[50];
        View view =inflater.inflate(R.layout.fragment_search,container,false);

        SearchView searchView=(SearchView)view.findViewById(R.id.searchView);
        ListView listView=(ListView)view.findViewById(R.id.myList);


        restaurant = new ArrayList<String>();
        restaurants_search=new ArrayList<String>();
        //adapter=new ArrayAdapter<>(getClass(), android.R.layout.simple_list_item_1,restaurants);
        //listView.setAdapter(adapter);

        DatabaseAdapter databaseAdapter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            databaseAdapter = new DatabaseAdapter(getContext());
        }
        Restaurant restaurants[] = databaseAdapter.getRestaurants();
        for(int i=0;i<restaurants.length;i++) {
            getRestaurantData[i]= restaurants[i].getValues();
        }
        for(int i=0;i<restaurants.length;i++){
            restaurantName[i]= getRestaurantData[i][0];
            restaurant.add(restaurantName[i]);
            ratecounter[i]=Integer.parseInt(getRestaurantData[i][1]);
            ratevalue[i]=Integer.parseInt(getRestaurantData[i][2]);
            imagePath[i]= getRestaurantData[i][3];
            total_rate[i]=(ratevalue[i]/ratecounter[i]*1.0f);
        }


        // final Context context=this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchMethod(query);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(getContext() ,query+"selected", Toast.LENGTH_LONG
                    ).show();
                    Bundle bundle= new Bundle();
                    for(int i=0;i<restaurantName.length;++i) {
                        if (query.equals(restaurantName[i])){
                            bundle.putString("name", query);
                            bundle.putString("image_path",imagePath[i]);
                            bundle.putFloat("totalrate",total_rate[i]);

                        }
                    }

                    Fragment f=new RestuarantFragment();
                    Fragment fragment = new RestaurantFragment2();
                    fragment.setArguments(bundle);
                    ((FragmentActivity) getContext()).getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                    ((FragmentActivity) getContext()).getFragmentManager().beginTransaction().hide(f);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                searchMethod(newText);
                return false;
            }
            public void searchMethod(String word)
            {
                restaurants_search.clear();
                for (int i=0;i<restaurant.size();i++)
                {
                    String name =restaurant.get(i);
                    if(name.contains(word))
                    {
                        restaurants_search.add(name);
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    adapter= new ArrayAdapter<String>(getContext() , android.R.layout.simple_list_item_1,restaurants_search);
                }
                listView.setAdapter(adapter);
            }

        });






    return view;
    }



}
