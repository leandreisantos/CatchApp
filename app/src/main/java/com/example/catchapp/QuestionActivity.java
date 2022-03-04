package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuestionActivity extends AppCompatActivity {

    TextView close;
    TextView happy,sad,angry;

    String currentuid;
    databaseReference dbr = new databaseReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance(dbr.keyDb());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentuid = user.getUid();

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

    @Override
    protected void onStart() {
        super.onStart();

        DocumentReference reference1;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        reference1 = firestore.collection("user").document(currentuid);
        reference1.get()
                .addOnCompleteListener(task -> {
                    if(!task.getResult().exists()){
                        Intent intent = new Intent(QuestionActivity.this,CreateProfileActivity.class);
                        startActivity(intent);
                    }
                });
    }
}