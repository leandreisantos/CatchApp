package com.example.catchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    TextView password,confpassword;
    EditText passholder,emailholder,confpassholder;
    Boolean ishowpass = false,ishowconfpass = false;
    ProgressBar pbholder;
    ImageButton submitholder;
    ImageView backholder;

    //firebase
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        password = findViewById(R.id.et_view_pass);
        confpassword = findViewById(R.id.et_view_pass2);
        passholder = findViewById(R.id.et_password_rg);
        confpassholder = findViewById(R.id.et_password2_rg);
        pbholder = findViewById(R.id.pv_login);
        submitholder = findViewById(R.id.sign_up_rg);
        backholder = findViewById(R.id.btn_back_rg);
        emailholder = findViewById(R.id.et_username_rg);

        backholder.setOnClickListener(view -> onBackPressed());

        password.setOnClickListener(view -> {
            if(ishowpass){
                passholder.setInputType(129);
                password.setBackgroundResource(R.drawable.vision_logo);
                ishowpass = false;
            }else{
                passholder.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                password.setBackgroundResource(R.drawable.visible_off_icon);
                ishowpass = true;
            }

        });
        confpassword.setOnClickListener(view -> {
            if(ishowconfpass){
                confpassholder.setInputType(129);
                confpassword.setBackgroundResource(R.drawable.vision_logo);
                ishowconfpass = false;
            }else{
                confpassholder.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                confpassword.setBackgroundResource(R.drawable.visible_off_icon);
                ishowconfpass = true;
            }

        });

        submitholder.setOnClickListener(view -> submitaccount());
    }

    private void submitaccount() {
        String email = emailholder.getText().toString();
        String pass = passholder.getText().toString();
        String confirm_password = confpassholder.getText().toString();
        process(true);
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_password)){
            if(pass.equals(confirm_password)){
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(SignUpActivity.this,SigInActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignUpActivity.this,"Successfully created your account enjoy!", Toast.LENGTH_SHORT).show();
                        pbholder.setVisibility(View.INVISIBLE);
                    }else{
                        process(false);
                        String error = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(SignUpActivity.this, "Error:"+error, Toast.LENGTH_SHORT).show();
                        pbholder.setVisibility(View.INVISIBLE);
                    }
                });
            }else{
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SignUpActivity.this, "password and confirm password is not matching", Toast.LENGTH_SHORT).show();
                process(false);
            }
        }else{
            Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            process(false);

        }

    }

    private void process(boolean b) {
        if(b) {
            pbholder.setVisibility(View.VISIBLE);
            emailholder.setEnabled(false);
            passholder.setEnabled(false);
            confpassholder.setEnabled(false);
            backholder.setEnabled(false);
            submitholder.setEnabled(false);
        }else{
            pbholder.setVisibility(View.GONE);
            emailholder.setEnabled(true);
            passholder.setEnabled(true);
            confpassholder.setEnabled(true);
            backholder.setEnabled(true);
            submitholder.setEnabled(true);
        }
    }
}