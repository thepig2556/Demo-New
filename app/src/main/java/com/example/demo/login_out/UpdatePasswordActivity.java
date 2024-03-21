package com.example.demo.login_out;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.SecondActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {
    EditText edtCurrentPass, edtNewPass, edtRePass;
    Button btnUpdate, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        edtCurrentPass = findViewById(R.id.edtCurrentPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtRePass = findViewById(R.id.edtRePass);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnBack=findViewById(R.id.btnBack);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String oldPassword, newPassword, rePassword;
                oldPassword = edtCurrentPass.getText().toString().trim();
                newPassword = edtNewPass.getText().toString().trim();
                rePassword = edtRePass.getText().toString().trim();
                if (newPassword.length()<6)
                {
                    Toast.makeText(UpdatePasswordActivity.this, "Mật khẩu không được dưới 6 ký tự", Toast.LENGTH_SHORT).show();
                }
                if (!newPassword.equals(rePassword))
                {
                    Toast.makeText(UpdatePasswordActivity.this, "Mật khẩu mới và Xác nhận mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                }
                else {
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(UpdatePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                edtCurrentPass.setText("");
                                edtNewPass.setText("");
                                edtRePass.setText("");
                                Intent intent = new Intent(UpdatePasswordActivity.this, SecondActivity.class);
                                startActivity(intent);
                            }
                            else{
                                edtCurrentPass.setText("");
                                edtNewPass.setText("");
                                edtRePass.setText("");
                                Toast.makeText(UpdatePasswordActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdatePasswordActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}