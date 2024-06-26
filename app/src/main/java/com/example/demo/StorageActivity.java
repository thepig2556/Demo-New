package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StorageActivity extends AppCompatActivity {
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Model> options;
    EditText inputSearch;
    FirebaseAuth auth;
    FirebaseUser user;
    long maxid = 0;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        mLinearLayoutManager=new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        inputSearch=findViewById(R.id.inputSearch);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView=findViewById(R.id.recyclerView);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        getSupportActionBar().setTitle("CATEGORY");
        mDatabaseReference= mFirebaseDatabase.getReference("Data");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
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
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //
            }
        });
        showData("");
        //
//        linearItem=findViewById(R.id.linear_item);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!=null)
                {
                    showData(s.toString());
                }
                else {
                    showData("");
                }
            }
        });
    }

    //public void showData(String data)
    private void showData(String data) {
        Query query = mDatabaseReference.orderByChild("title").startAt(data).endAt(data+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(query,Model.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                //Set on Click Item List Chapter
                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent( StorageActivity.this, ListChapterActivity.class);
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
                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.menu,menu);
        MenuInflater mnu2 = getMenuInflater();
        mnu2.inflate(R.menu.menu_second,menu);
        MenuInflater mnu3 = getMenuInflater();
        mnu3.inflate(R.menu.menu_third,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.home)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.mnAdd){
            {
                if(user!=null)
                {
                    String s = "thepig6704@gmail.com";
                    if (user.getEmail().equals(s))
                    {
                        Intent intent = new Intent(this, InsertAcitivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(item.getItemId()==R.id.mnRetrieve){
            {
                if(user!=null)
                {
                    String s = "thepig6704@gmail.com";
                    if (user.getEmail().equals(s))
                    {
                        Intent intent = new Intent(this, RetrieveDataActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(item.getItemId()==R.id.addGenre){
            {
                if(user!=null)
                {
                    String s = "thepig6704@gmail.com";
                    if (user.getEmail().equals(s))
                    {
                        Intent intent = new Intent(this, AddGenreActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "You must login to admin account to use this system", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(item.getItemId()==R.id.logout){
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }


        if (item.getItemId()==R.id.genreAll)
        {
            showData("");
        }

        if(item.getItemId()==R.id.genreRomance)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Romance"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreDetective)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Detective"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreAdventure)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Adventure"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreAction)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Action"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genrePsychological)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Psychological"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreComedy)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Comedy"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreFantasy)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Fantasy"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreShounen)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Shounen"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreDrama)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Drama"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreHorror)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Horror"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
        if(item.getItemId()==R.id.genreScience)
        {
            options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mDatabaseReference.orderByChild("genre").equalTo("Science"),Model.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder,final int position, @NonNull Model model) {
                    holder.setDetails(getApplicationContext(),model.getTitle(), model.getImage(), model.getAuthor(), model.getLuotxem());
//                Click chapter
                    //Set on Click Item List Chapter
                    holder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent( StorageActivity.this,ListChapterActivity.class);
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
                    View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
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
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }

        return super.onOptionsItemSelected(item);
    }
}