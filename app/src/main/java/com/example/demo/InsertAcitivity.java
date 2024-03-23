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

import java.util.ArrayList;

public class InsertAcitivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nameAdd,authorAdd,imgAdd, viewAdd;
    Button btnAdd, btnBack;
    DatabaseReference mangaDbRef, dbRef;
    Spinner spinner;
    String item;
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    long maxid=0; //Biến đặt trên firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_acitivity);

        spinner=findViewById(R.id.spinnerAdd);
        list=new ArrayList<String>();
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,genre);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(arrayAdapter);

        nameAdd = findViewById(R.id.nameAdd);
        imgAdd = findViewById(R.id.imgAdd);
        authorAdd = findViewById(R.id.authorAdd);
        viewAdd = findViewById(R.id.viewAdd);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        getSupportActionBar().setTitle("Thêm truyện");

        dbRef = FirebaseDatabase.getInstance().getReference("Genre Data");
        //load genre
        fetchData();

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

    private void fetchData() {
        listener=dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata : snapshot.getChildren() )
                {
                    list.add(mydata.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void insertManga() {
            String name = nameAdd.getText().toString();
            String img = imgAdd.getText().toString();
            String author = authorAdd.getText().toString();
            long id = maxid;
            long view = Long.parseLong(viewAdd.getText().toString());
            Model manga = new Model(id,name,img,author,view);
            manga.setGenre(spinner.getSelectedItem().toString());
            mangaDbRef.child(Long.toString(id)).setValue(manga);
            Toast.makeText(this, "Insert Succesful", Toast.LENGTH_SHORT).show();
            nameAdd.setText("");
            imgAdd.setText("");
            authorAdd.setText("");
            viewAdd.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}