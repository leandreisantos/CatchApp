package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this,SigInActivity.class);
            startActivity(intent);
        },3000);
    }
}