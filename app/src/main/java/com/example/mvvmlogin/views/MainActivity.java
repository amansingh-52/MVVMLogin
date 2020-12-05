package com.example.mvvmlogin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mvvmlogin.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {

    }
}