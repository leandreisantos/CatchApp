package com.example.catchapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CreateProfileActivity extends AppCompatActivity {

    ImageView ivholder;
    EditText nameholder,profholder,descholder;
    TextView submit,piclogo;
    ProgressBar pb;

    databaseReference dbr = new databaseReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance(dbr.keyDb());
    DatabaseReference databaseReference;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;

    UploadTask uploadTask;
    StorageReference storageReference;
    AllUserMember member;

    private static final int PICK_IMAGE=1;
    Uri imageUridp;

    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        member= new AllUserMember();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        documentReference = db.collection("user").document(currentUserId);
        storageReference = FirebaseStorage.getInstance().getReference("Profile images");
        databaseReference = database.getReference("All users");

        ivholder = findViewById(R.id.iv_cp);
        nameholder = findViewById(R.id.et_name_cp);
        profholder = findViewById(R.id.et_prof_cp);
        descholder = findViewById(R.id.et_desc_cp);
        submit = findViewById(R.id.tv_sub);
        piclogo = findViewById(R.id.pic_logo);
        pb = findViewById(R.id.pv_cp);



        ivholder.setOnClickListener(v -> chooseImage());

        submit.setOnClickListener(v -> uploadProfile());
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!=null){
                imageUridp = data.getData();
                piclogo.setVisibility(View.GONE);
                Picasso.get().load(imageUridp).into(ivholder);
            }

        }catch (Exception e){
            Toast.makeText(this, "Error"+e, Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
    }

    private void uploadProfile() {

        String name = nameholder.getText().toString();
        String prof = profholder.getText().toString();
        String desc = descholder.getText().toString();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(prof) && !TextUtils.isEmpty(desc) && imageUridp != null) {
            pb.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUridp));
            uploadTask = reference.putFile(imageUridp);

            Task<Uri> urlTask = uploadTask.continueWithTask((Task<UploadTask.TaskSnapshot> task) -> {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                    Uri downloadUri = task.getResult();

                    Map<String,String> profile = new HashMap<>();
                    profile.put("name",name);
                    profile.put("prof",prof);
                    profile.put("url",downloadUri.toString());
                    profile.put("desc",desc);
                    profile.put("uid",currentUserId);


                    member.setName(name.toUpperCase());
                    member.setProf(prof);
                    member.setUid(currentUserId);
                    member.setUrl(downloadUri.toString());
                    member.setDesc(desc);

                    databaseReference.child(currentUserId).setValue(member);

                    documentReference.set(profile)
                            .addOnSuccessListener(aVoid -> {
                                pb.setVisibility(View.INVISIBLE);
                                Toast.makeText(CreateProfileActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();


                                Handler handler = new Handler();
                                handler.postDelayed(() -> {
                                    Intent intent = new Intent(CreateProfileActivity.this,QuestionActivity.class);
                                    startActivity(intent);
                                    finish();
                                },2000);
                            });
            });

        }else{
            Toast.makeText(CreateProfileActivity.this, "Please Fill up All Requirements!", Toast.LENGTH_SHORT).show();
        }

    }
}