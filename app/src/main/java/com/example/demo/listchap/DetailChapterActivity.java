package com.example.demo.listchap;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demo.R;
import com.example.demo.adapter.ChapterAdapter;
import com.example.demo.object.chapter_Ten_ngaydang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailChapterActivity extends AppCompatActivity {

    ImageView imgAnh;
    ArrayList<String> arrURLAnh;
    int SoTrang, Sotrangdangdoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_chapter);

        intro();
        setClick();
        setUp();
        Anhxa();
        imgAnh = findViewById(R.id.imgAnh);
        //Du lieu ao cua chapter detail
        arrURLAnh = new ArrayList<>();
        arrURLAnh.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCrcTSmNMnp6LtgpG5UMYwX-LsR_GlCMe1lw&usqp=CAU");
        arrURLAnh.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQ1o9xj9AIPl62yvLYhyq21-ebcXaoJpEByg&usqp=CAU");
        arrURLAnh.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQ1o9xj9AIPl62yvLYhyq21-ebcXaoJpEByg&usqp=CAU");
        arrURLAnh.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSpWRxARLimFOUw9Vz8T7ZhtaG0tOR0lAK-_Q&usqp=CAU");
        arrURLAnh.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCrcTSmNMnp6LtgpG5UMYwX-LsR_GlCMe1lw&usqp=CAU");
        arrURLAnh.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxPlaARuzx4ekSiFy4HZ_POGc6jj2rHzjaGQ&usqp=CAU");

        Sotrangdangdoc = 0;
        SoTrang = arrURLAnh.size();
    }

    private void intro() {


    }


    private void setUp() {


    }

    private void Anhxa() {


    }

    private void setClick() {

    }
    public void left(View view) {
        doctheotrang(-1);
    }

    public void right(View view) {
        doctheotrang(1);

    }

    private void doctheotrang(int i) {
        Sotrangdangdoc = Sotrangdangdoc + i;
        if (Sotrangdangdoc == 0) {
            Sotrangdangdoc = 1;
        }
        if (Sotrangdangdoc > SoTrang) {
            Sotrangdangdoc = SoTrang;

        }
        Picasso.get().load(arrURLAnh.get(Sotrangdangdoc-1)).into(imgAnh);
    }
}