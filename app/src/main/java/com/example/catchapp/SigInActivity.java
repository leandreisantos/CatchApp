package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SigInActivity extends AppCompatActivity {

    TextView register,showpass;
    CardView cvholder;
    ProgressBar pbholder;
    EditText emailholder,passholder;
    Boolean ishowpass = false;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_in);

        register = findViewById(R.id.tv_signup);
        cvholder = findViewById(R.id.cv);
        pbholder = findViewById(R.id.pv_login);
        emailholder = findViewById(R.id.et_email_si);
        passholder = findViewById(R.id.et_pass_si);
        showpass = findViewById(R.id.et_view_pass);

        showpass.setOnClickListener(view -> {
            if(ishowpass){
                passholder.setInputType(129);
                showpass.setBackgroundResource(R.drawable.vision_logo);
                ishowpass = false;
            }else{
                passholder.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showpass.setBackgroundResource(R.drawable.visible_off_icon);
                ishowpass = true;
            }
        });


        register.setOnClickListener(view -> {
            Intent intent = new Intent(SigInActivity.this,SignUpActivity.class);
            startActivity(intent);
        });
        cvholder.setOnClickListener(view -> {
            login();
        });
    }

    private void login() {
        String email = emailholder.getText().toString();
        String password = passholder.getText().toString();

        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
            pbholder.setVisibility(View.VISIBLE);
            enabledElement(false);

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Intent intent = new Intent(SigInActivity.this,QuestionActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    pbholder.setVisibility(View.GONE);
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(SigInActivity.this, "Error:"+error, Toast.LENGTH_SHORT).show();
                    enabledElement(true);
                }
            });
        }else Toast.makeText(SigInActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
    }

    private void enabledElement(Boolean x){
        emailholder.setEnabled(x);
        passholder.setEnabled(x);
        register.setEnabled(x);
        cvholder.setEnabled(x);

    }
}