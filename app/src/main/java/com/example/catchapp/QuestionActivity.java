package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    TextView close;
    TextView happy,sad,angry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        close = findViewById(R.id.tv_close_q);
        happy = findViewById(R.id.tv_happy_q);
        sad = findViewById(R.id.tv_sad_q);
        angry = findViewById(R.id.tv_angry_q);

        close.setOnClickListener(view -> {
            Intent intent = new Intent(QuestionActivity.this,MainActivity.class);
            startActivity(intent);
        });

        happy.setOnClickListener(view -> {
            Intent intent = new Intent(QuestionActivity.this,QuotesActivity.class);
            intent.putExtra("k","happy");
            startActivity(intent);
        });
        sad.setOnClickListener(view -> {
            Intent intent = new Intent(QuestionActivity.this,QuotesActivity.class);
            intent.putExtra("k","sad");
            startActivity(intent);
        });
        angry.setOnClickListener(view -> {
            Intent intent = new Intent(QuestionActivity.this,QuotesActivity.class);
            intent.putExtra("k","angry");
            startActivity(intent);
        });


    }
}