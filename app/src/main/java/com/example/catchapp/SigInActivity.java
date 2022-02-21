package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SigInActivity extends AppCompatActivity {

    TextView register;
    CardView cvholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_in);

        register = findViewById(R.id.tv_signup);
        cvholder = findViewById(R.id.cv);


        register.setOnClickListener(view -> {
            Intent intent = new Intent(SigInActivity.this,SignUpActivity.class);
            startActivity(intent);
        });
        cvholder.setOnClickListener(view -> {
            Intent intent = new Intent(SigInActivity.this,QuestionActivity.class);
            startActivity(intent);
        });
    }
}