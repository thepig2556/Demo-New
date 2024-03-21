package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demo.login_out.activity_login;
import com.example.demo.object.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InsertAcitivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nameAdd,authorAdd,imgAdd, viewAdd;
    Button btnAdd, btnBack;
    DatabaseReference mangaDbRef;
    FirebaseAuth auth;
    FirebaseUser user;
    Spinner spinner;
    String item;
    String[] genre ={"Choose genre","Comedy","Slice of life","Drama","Horror"};
    long maxid=0; //Biến đặt trên firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_acitivity);

        spinner=findViewById(R.id.spinnerAdd);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,genre);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        nameAdd = findViewById(R.id.nameAdd);
        imgAdd = findViewById(R.id.imgAdd);
        authorAdd = findViewById(R.id.authorAdd);
        viewAdd = findViewById(R.id.viewAdd);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        //ko cho vao khi chua dang nhap

        auth=FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
//        if(user==null)
//        {
//            Intent intent = new Intent(InsertAcitivity.this, MainActivity.class);
//            startActivity(intent);
//            Toast.makeText(this, "You must login to use this system", Toast.LENGTH_SHORT).show();
//        }
//        if (user.getEmail()==s){
//            Toast.makeText(this, "WELCOME BACK", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            Toast.makeText(this, "You must login with admin account to continue", Toast.LENGTH_SHORT).show();
//        }

        //truy xuất dữ liệu đến KEY "Data"
        mangaDbRef = FirebaseDatabase.getInstance().getReference("Data");
        mangaDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    maxid=snapshot.getChildrenCount();
                    //Tăng dữ liệu maxid lên nếu tồn tại truyện
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertManga();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertAcitivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertManga() {
        if(item=="Choose genre")
        {
            Toast.makeText(this, "Please select genre for this manga", Toast.LENGTH_SHORT).show();
        }
        else{
            String name = nameAdd.getText().toString();
            String img = imgAdd.getText().toString();
            String author = authorAdd.getText().toString();
            String id = String.valueOf(maxid);
            String view = viewAdd.getText().toString();
            Model manga = new Model(id,name,img,author,view);
            assert id != null;
            manga.setGenre(item);
            mangaDbRef.child(id).setValue(manga);
            Toast.makeText(this, "Insert Succesful", Toast.LENGTH_SHORT).show();
            nameAdd.setText("");
            imgAdd.setText("");
            authorAdd.setText("");
            viewAdd.setText("");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item=spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}