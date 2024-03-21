package com.example.demo.login_out;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.ForgotPasswordActivity;
import com.example.demo.MainActivity;
import com.example.demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class activity_login extends AppCompatActivity {
    EditText useredit, passedit;
    Button btnlogin, btnregister;
    TextView txtForgotPassword;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        useredit = findViewById(R.id.dangnhap);
        passedit = findViewById(R.id.matkhau);
        btnlogin = findViewById(R.id.btnlogin);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        btnregister = findViewById(R.id.btnregister);
        mAuth = FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, activity_register.class);
                startActivity(intent);
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }


    private void login() {
        String user, pass;
        user = useredit.getText().toString();
        pass = passedit.getText().toString();
        if(TextUtils.isEmpty(user)){
            Toast.makeText(this,"Email không được trống",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Mật khẩu không được trống",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_login.this, MainActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(activity_login.this, "Vui lòng xác nhận Email", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.menu_second,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.home)
        {
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}