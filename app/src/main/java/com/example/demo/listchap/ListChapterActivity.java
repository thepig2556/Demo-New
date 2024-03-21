package com.example.demo.listchap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.CommentsActivity;
import com.example.demo.object.Model;
import com.example.demo.R;
import com.example.demo.adapter.ChapterAdapter;
import com.example.demo.object.chapter_Ten_ngaydang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListChapterActivity extends AppCompatActivity {
    ChapterAdapter chapterAdapter;
    ListView lvChapTer;
    ArrayList<chapter_Ten_ngaydang> arr;
    Model model;
    ImageButton add_commentsbtn;

TextView tvTenMG;
ImageView img_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_chapter);

        lvChapTer=findViewById(R.id.lvChapTer);
        add_commentsbtn=findViewById(R.id.add_commentsbtn);
        //on click Detail chapter


        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle==null)
        {
            return;
        }
        Model model=(Model) bundle.getSerializable("key");
        setClick();
        setUp();
        Anhxa();
        intro();
        String image= model.getImage();
        String title=model.getTitle();
        getSupportActionBar().setTitle(title);
        tvTenMG=findViewById(R.id.txtTenMG);
//tvTenMG.setText(getTitle());

        img_detail=findViewById(R.id.img_detail);
        ImageView img_detail=findViewById(R.id.img_detail);
        tvTenMG.setText(title);
        Picasso.get().load(image).into(img_detail);


//        tvTenMG=;
//        lvChapTer.setAdapter(chapterAdapter);

        //Tới đây làm được

//     lvChapTer.setAdapter(chapterAdapter);
//
//        chapterAdapter=new ChapterAdapter(this,0,arrChap);
//        lvChapTer.setAdapter((ListAdapter) chapterAdapter);

        //Du lieu ao cua list chap
//        arr=new ArrayList<>();
//        for(int i=0;i<20;i++){
//            arr.add(new chapter_Ten_ngaydang("Chapter"+i ,"06-2-2024"));
//        }
//        chapterAdapter=new ChapterAdapter(this,0,arr);

    }

    private void intro (){
        //Du lieu ao cua list chap
        arr=new ArrayList<>();
        for(int i=0;i<20;i++){
            arr.add(new chapter_Ten_ngaydang("Chapter"+i ,"06-2-2024"));
        }
        chapterAdapter=new ChapterAdapter(this,0,arr);
        //        tvTenMG.setText(getTitle());
        lvChapTer.setAdapter(chapterAdapter);
    }



    private void setUp(){


    }
    private void Anhxa()
    {


    }
    private void setClick(){
        //Click Item chapter
        lvChapTer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ListChapterActivity.this,DetailChapterActivity.class));
            }
        });

        add_commentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListChapterActivity.this,CommentsActivity.class));
            }
        });

    }
}