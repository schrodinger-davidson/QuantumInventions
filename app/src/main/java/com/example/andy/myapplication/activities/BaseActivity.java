package com.example.andy.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.andy.myapplication.network.NetworkConnection;


// Base Class for all activites to contains comman features of Activity
public class BaseActivity extends AppCompatActivity {

    public NetworkConnection networkConnection ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkConnection = new NetworkConnection(this, this);
    }
}
