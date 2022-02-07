package com.example.foodordringapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodordringapp.bottomBarFragment.CartFragmrent;
import com.example.foodordringapp.bottomBarFragment.FavouritsFragmrent;
import com.example.foodordringapp.bottomBarFragment.HomeFragmrent;
import com.example.foodordringapp.bottomBarFragment.RestuarantFragment;
import com.example.foodordringapp.bottomBarFragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //DatabaseAdapter databaseAdapter=new DatabaseAdapter(getApplicationContext());
        //databaseAdapter.deleteTitle(",dj");
        //HomeFragmrent home = new HomeFragmrent();

      /*  DatabaseAdapter databaseAdapter=new DatabaseAdapter(getApplicationContext());
databaseAdapter.updateRate("Buffalo Burger");*/

        //FragmentManager fragmentManager = getSupportFragmentManager();
       // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.fragment_container, home);
        //fragmentTransaction.commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
          //      new HomeFragmrent()).commit();

        FragmentManager fragmentManager2 = getFragmentManager();
        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
        fragmentTransaction2.replace(R.id.fragment_container,new HomeFragmrent()).addToBackStack(null).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment SelectedItem = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            SelectedItem = new HomeFragmrent();
                            break;
                        case R.id.nav_fav:
                            SelectedItem = new FavouritsFragmrent();
                            break;
                        case R.id.nav_cart:
                            SelectedItem = new CartFragmrent();
                            break;
                        case R.id.nav_search:
                            SelectedItem = new SearchFragment();
                            break;
                        case R.id.nav_res:
                            SelectedItem = new RestuarantFragment();
                            break;

                    }
                    FragmentManager fragmentManager2 = getFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                    fragmentTransaction2.replace(R.id.fragment_container,SelectedItem).addToBackStack(null).commit();
                    return true;
                }
            };


}