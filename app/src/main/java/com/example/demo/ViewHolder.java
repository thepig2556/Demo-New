package com.example.demo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview=itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return false;
            }
        });
    }
    public void setDetails(Context ctx, String title, String image, String author, String view){
        TextView mTitle=mview.findViewById(R.id.rTitleMG);
        TextView mAuthor=mview.findViewById(R.id.rNameAuthor);
        ImageView mImage=mview.findViewById(R.id.rImage);
        TextView mView=mview.findViewById(R.id.rView);
        mTitle.setText(title);
        mAuthor.setText(author);
        Picasso.get().load(image).into(mImage);
        mView.setText(view);
    }
    private ViewHolder.ClickListener mClickListener;



    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener=clickListener;
    }
    //on click chapter
//    public void setChapter(Context ctx, String title, String image){
//        TextView tvTenMG=mview.findViewById(R.id.txtTenMG);
//        ImageView img_detail=mview.findViewById(R.id.img_detail);
//        tvTenMG.setText(title);
//        Picasso.get().load(image).into(img_detail);
//    }
}
