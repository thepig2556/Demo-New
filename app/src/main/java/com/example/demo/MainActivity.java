package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.demo.listchap.ListChapterActivity;
import com.example.demo.object.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayoutManager mLinearLayoutManager, layoutManager, hLayoutManager;
    RecyclerView mRecyclerView, mRecyclerView2, mRecyclerView3;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter, firebaseRecyclerAdapter2, firebaseRecyclerAdapter3;
    FirebaseRecyclerOptions<Model>options, options2, options3;
    EditText inputSearch;
    LinearLayout linearItem;
    TextView btnShowAll;
    //
    FirebaseAuth auth;
    FirebaseUser user;
    ImageSlider mainSlider;

    //
    long maxid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        hLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        hLayoutManager.setReverseLayout(true);
        hLayoutManager.setStackFromEnd(true);

        mLinearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLinearLayoutManager.setReverseLayout(true);
//        inputSearch=findViewById(R.id.inputSearch);
        btnShowAll = findViewById(R.id.btnShowAll);

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, StorageActivity.class);
                startActivity(intent);
            }
        });
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView2=findViewById(R.id.recyclerView2);
        mRecyclerView3=findViewById(R.id.recyclerView3);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference= mFirebaseDatabase.getReference("Data");

        //
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    maxid= snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //

        //BANNER AUTOMATIC
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainSlider=(ImageSlider)findViewById(R.id.imageSlider);
        final List<SlideModel> remoteimages=new ArrayList<>();
        mFirebaseDatabase.getReference().child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    remoteimages.add(new SlideModel(data.child("image").getValue().toString(),data.child("title").getValue().toString().toUpperCase(), ScaleTypes.FIT));
                }
                mainSlider.setImageList(remoteimages,ScaleTypes.FIT);
                mainSlider.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemSelected(int i) {
//                        Toast.makeText(MainActivity.this, remoteimages.get(i).getTitle().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void doubleClick(int i) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //
            }
        });
        showData();
        //
//        linearItem=findViewById(R.id.linear_item);
//        inputSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString()!=null)
//                {
//                    showData(s.toString());
//                }
//            }
//        });
    }
    //Tăng lượt xem khi nhấn vào


    public void onMangaClicked(String mangaId){

    }

//public void showData(String data)
    private void showData() {
        options2 = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.limitToLast(7),Model.class).build();
        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.limitToFirst(7),Model.class).build();
        options3 = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("luotxem").limitToLast(7), Model.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                holder.setDetails2(getApplicationContext(),model.getTitle(), model.getImage());
//                Click chapter
                //Set on Click Item List Chapter
                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent( MainActivity.this,ListChapterActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("key",model);
                        mDatabaseReference.child(String.valueOf(model.getId())).child("luotxem").setValue(model.getLuotxem()+1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        //
//        dữ iệu ảo list chapter
                    }
                });
            }
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row2,parent,false);
                ViewHolder viewHolder=new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //               Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };

        firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<Model, ViewHolder>(options2) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                holder.setDetails2(getApplicationContext(),model.getTitle(), model.getImage());
                //                Click chapter
                //Set on Click Item List Chapter
                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent( MainActivity.this,ListChapterActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("key",model);
                        intent.putExtras(bundle);
                        mDatabaseReference.child(String.valueOf(model.getId())).child("luotxem").setValue(model.getLuotxem()+1);
                        startActivity(intent);
                        //
//        dữ iệu ảo list chapter
                    }
                });
            }


            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row2,parent,false);
                ViewHolder viewHolder=new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //               Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };



        firebaseRecyclerAdapter3 = new FirebaseRecyclerAdapter<Model, ViewHolder>(options3) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                holder.setDetails2(getApplicationContext(),model.getTitle(), model.getImage());
//                Click chapter
                //Set on Click Item List Chapter
                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent( MainActivity.this,ListChapterActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("key",model);
                        mDatabaseReference.child(String.valueOf(model.getId())).child("luotxem").setValue(model.getLuotxem()+1);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        //
//        dữ iệu ảo list chapter
                    }
                });
            }
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row2,parent,false);
                ViewHolder viewHolder=new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //               Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };
        mRecyclerView3.setLayoutManager(hLayoutManager);
        firebaseRecyclerAdapter3.startListening();
        mRecyclerView3.setAdapter(firebaseRecyclerAdapter3);



        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


        mRecyclerView2.setLayoutManager(layoutManager);
        firebaseRecyclerAdapter2.startListening();
        mRecyclerView2.setAdapter(firebaseRecyclerAdapter2);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseRecyclerAdapter!=null){

            firebaseRecyclerAdapter.startListening();
        }
        if(firebaseRecyclerAdapter2!=null){

            firebaseRecyclerAdapter2.startListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnAdd) {
            {
                if (user != null) {
                    String s = "thepig6704@gmail.com";
                    if (user.getEmail().equals(s)) {
                        Intent intent = new Intent(this, InsertAcitivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (item.getItemId() == R.id.mnRetrieve) {
            {
                if (user != null) {
                    String s = "thepig6704@gmail.com";
                    if (user.getEmail().equals(s)) {
                        Intent intent = new Intent(this, RetrieveDataActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (item.getItemId() == R.id.addGenre) {
            {
                if (user != null) {
                    String s = "thepig6704@gmail.com";
                    if (user.getEmail().equals(s)) {
                        Intent intent = new Intent(this, AddGenreActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}