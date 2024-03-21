package com.example.demo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.demo.login_out.UpdatePasswordActivity;
import com.example.demo.login_out.activity_login;
import com.example.demo.profilepic.AndroidUtil;
import com.example.demo.profilepic.FirebaseUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SecondActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button btnSignout, btnBack, btnChangePass,btnChangeProfilePic;
    TextView tvUserEmail;
    ImageView profilepic;
    FirebaseUser user;
    ActivityResultLauncher<Intent> imagePickLauncher;//profile pic
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        auth = FirebaseAuth.getInstance();
        btnSignout = findViewById(R.id.btnSignout);
        btnBack = findViewById(R.id.btnBack);
        btnChangePass = findViewById(R.id.btnChangePassword);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        profilepic = findViewById(R.id.profilepic);
        btnChangeProfilePic= findViewById(R.id.btnChangeProfilePic);
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), activity_login.class);
            startActivity(intent);
        } else {
            tvUserEmail.setText(user.getEmail());
        }

        imagePickLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode()==Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri=data.getData();
                            AndroidUtil.setProfilePic(getApplicationContext(),selectedImageUri,profilepic);
                        }
                    }
                });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), activity_login.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(SecondActivity.this).cropSquare().compress(512).maxResultSize(512,512)
                .createIntent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        imagePickLauncher.launch(intent);
                        return null;
                    }
                });
            }
        });

        btnChangeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtil.getCurrentProfilePicStorage().putFile(selectedImageUri)
                        .addOnCompleteListener(task -> {
                            updatetoFireStore();
                        });
            }
        });
    }

    private void updatetoFireStore() {
    }

    void getUserData(){
        FirebaseUtil.getCurrentProfilePicStorage().getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri uri = task.getResult();
                            AndroidUtil.setProfilePic(getApplicationContext(),uri,profilepic);
                        }
                    }
                });
    }

    void setInProgress(boolean inProgress){
        if(inProgress){
            btnChangeProfilePic.setVisibility(View.GONE);
        }
        else{

        }
    }

}