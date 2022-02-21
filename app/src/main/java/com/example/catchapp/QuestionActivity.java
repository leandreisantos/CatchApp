package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    TextView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        close = findViewById(R.id.tv_close_q);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}