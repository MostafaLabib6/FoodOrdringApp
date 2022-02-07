package com.example.foodordringapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Getstarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button b=(Button)findViewById(R.id.home_btn);
        b.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));
    }

}