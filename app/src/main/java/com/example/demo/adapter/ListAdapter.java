package com.example.demo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo.object.Model;
import com.example.demo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    private Activity mContext;
    List<Model> modelList;
    public ListAdapter(Activity mContext,List<Model> modelList){
        super(mContext, R.layout.list_items,modelList);
        this.mContext=mContext;
        this.modelList=modelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_items,null,true);
        TextView tvName = listItemView.findViewById(R.id.tvName);
        ImageView tvImage = listItemView.findViewById(R.id.tvImage);
        TextView tvAuthor = listItemView.findViewById(R.id.tvAuthor);
        TextView tvView = listItemView.findViewById(R.id.tvView);
        TextView tvGenre = listItemView.findViewById(R.id.tvGenre);

        Model manga = modelList.get(position);
        tvName.setText(manga.getTitle());
        Picasso.get().load(manga.getImage()).into(tvImage);
        tvAuthor.setText(manga.getAuthor());
        tvView.setText(String.valueOf(manga.getLuotxem()));
        tvGenre.setText(manga.getGenre());
        return listItemView;
    }
}
