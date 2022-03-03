package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class QuotesActivity extends AppCompatActivity {

    String keyword;
    LottieAnimationView lot;
    TextView q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            keyword = extras.getString("k");
        }
        else Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();


        lot = findViewById(R.id.loginlot);
        q = findViewById(R.id.quotes);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(keyword.equals("happy")){
            q.setText("“Everyday is a new day”");
        }else if(keyword.equals("sad")){
            q.setText("“Tears are words the heart cant express”");
        }else if(keyword.equals("angry")){
            q.setText("“Anger can be an expensive luxury”");
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(QuotesActivity.this,MainActivity.class);
            startActivity(intent);
        },5000);

    }
}