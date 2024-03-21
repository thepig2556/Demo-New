package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demo.adapter.ListAdapter;
import com.example.demo.object.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetrieveDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
ListView lvManga;
List<Model> mangaList;
DatabaseReference mangaDBRef, dbRef;

    Spinner spinner;
    String item;
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        lvManga = findViewById(R.id.lvManga);
        mangaList = new ArrayList<>();

        mangaDBRef = FirebaseDatabase.getInstance().getReference("Data");

        dbRef = FirebaseDatabase.getInstance().getReference("Genre Data");
        
        mangaDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mangaList.clear();
//hiện dữ liệu
                for(DataSnapshot mangaDatasnap : dataSnapshot.getChildren()){
                    Model model = mangaDatasnap.getValue(Model.class);
                    mangaList.add(model);
                }
                ListAdapter adapter = new ListAdapter(RetrieveDataActivity.this,mangaList);
                lvManga.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        lvManga.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Model models = mangaList.get(position);
                showUpdateDialog(models.getId(),models.getTitle(), models.getImage(), models.getAuthor(), models.getLuotxem());
                return false;
            }
        });
    }
    private void showUpdateDialog(String id,String title, String image, String author, String view)
    {
        AlertDialog.Builder mDialog =new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.update_database, null);
        mDialog.setView(mDialogView);

        EditText nameUpdate = mDialogView.findViewById(R.id.nameUpdate);
        EditText imageUpdate = mDialogView.findViewById(R.id.imgUpdate);
        EditText authorUpdate = mDialogView.findViewById(R.id.authorUpdate);
        EditText viewUpdate = mDialogView.findViewById(R.id.viewUpdate);
        Button btnUpdate = mDialogView.findViewById(R.id.btnUpdate);
        Button btnDelete = mDialogView.findViewById(R.id.btnDelete);


        //DROPDOWN MENU
        spinner=mDialogView.findViewById(R.id.genreUpdate);
        list=new ArrayList<String>();
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        fechData();



        nameUpdate.setText(title);
        imageUpdate.setText(image);
        authorUpdate.setText(author);
        viewUpdate.setText(view);

        mDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String newName = nameUpdate.getText().toString();
                    String newImage = imageUpdate.getText().toString();
                    String newAuthor = authorUpdate.getText().toString();
                    String newView = viewUpdate.getText().toString();
                    updateData(id,newName,newImage,newAuthor,newView);
                    mangaDBRef.child("/"+id+"/genre").setValue(spinner.getSelectedItem().toString());
                    Toast.makeText(RetrieveDataActivity.this, "Record Update", Toast.LENGTH_SHORT).show();
                    nameUpdate.setText("");
                    imageUpdate.setText("");
                    authorUpdate.setText("");
                    viewUpdate.setText("");
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord(id);
            }
        });
    }

    private void fechData() {
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

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void deleteRecord(String id) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Data").child(id);
        Task<Void> mTask = DbRef.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showToast("Deleted");
                Intent intent = new Intent(RetrieveDataActivity.this, RetrieveDataActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Error deleting record");
                Intent intent = new Intent(RetrieveDataActivity.this, RetrieveDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateData(String id,String title,String image, String author, String view){
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Data").child(id);
        Model models = new Model(id,title, image,author, view);
//        model.setGenre(item);
        DbRef.setValue(models);
    }
    @Override
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}