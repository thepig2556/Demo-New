package com.example.demo.login_out;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class activity_register extends AppCompatActivity {
    EditText useredit, passedit, repassedit;
    Button  btncreate, btnthoat;
    TextView tvHA;
    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        useredit = findViewById(R.id.dangnhap);
        passedit = findViewById(R.id.matkhau);
        repassedit = findViewById(R.id.xacnhanmatkhau);
        tvHA = findViewById(R.id.tvHA);
        btncreate = findViewById(R.id.btncreate);
        btnthoat = findViewById(R.id.btnthoat);

        mAuth = FirebaseAuth.getInstance();

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }


        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_register.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tvHA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_register.this, activity_login.class);
                startActivity(intent);
            }
        });
    }


    private void register() {
        String user, pass, repass;
        user = useredit.getText().toString();
        pass = passedit.getText().toString();
        repass = repassedit.getText().toString();

        if(TextUtils.isEmpty(user)){
            Toast.makeText(this,"Email không được trống",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Mật khẩu không được trống",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.length()<6)
        {
            Toast.makeText(this, "Mật khẩu không được dưới 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass==repass)
        {
            Toast.makeText(this, "Mật khẩu và Xác nhận mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }
        mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(activity_register.this, "Đăng ký thành công, vui lòng xác nhận Email", Toast.LENGTH_SHORT).show();
                                useredit.setText("");
                                passedit.setText("");
                                repassedit.setText("");
                                Intent intent = new Intent(activity_register.this, activity_login.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Email này đã tồn tại, vui lòng nhập Email khác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}