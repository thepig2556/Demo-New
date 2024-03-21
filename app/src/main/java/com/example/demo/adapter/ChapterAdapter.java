package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo.R;
import com.example.demo.object.chapter_Ten_ngaydang;

import java.util.ArrayList;

public class ChapterAdapter extends ArrayAdapter<chapter_Ten_ngaydang> {
    private Context ct;
    private ArrayList<chapter_Ten_ngaydang> arrChap;
    public ChapterAdapter(@NonNull Context context, int resource, @NonNull ArrayList<chapter_Ten_ngaydang> objects) {
        super(context, resource, objects);
        this.ct=context ;
        this.arrChap=new ArrayList<>(objects);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.chapter_item,null);

        }
        if (arrChap.size()>0){

            TextView tvSoChap=convertView.findViewById(R.id.tvSochap);
            TextView tvNgayDang=convertView.findViewById(R.id.tvNgaychap);
            chapter_Ten_ngaydang chapterTenNgaydang=arrChap.get(position);
            tvSoChap.setText(chapterTenNgaydang.getTenChap());
            tvNgayDang.setText(chapterTenNgaydang.getNgayDang());
        }
        return convertView;
    }
    //    private Context ct;
//    ArrayList<Model> arr;

//    public ChapterAdapter(@NonNull Context context, int resource, @NonNull List<Model> objects) {
//        super(context, resource, objects);
//        this.ct=context;
//this.arr=new ArrayList<>(objects);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        if(convertView==null)
//        {
//            LayoutInflater inflater=(LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView=inflater.inflate(R.layout.chapter_item,null);
//
//        }
//        if (arr.size()>0){
//
//            TextView tvSoChap=convertView.findViewById(R.id.tvSochap);
//TextView tvNgayDang=convertView.findViewById(R.id.tvNgaychap);
//Model model=arr.get(position);
//tvSoChap.setText(model.);
//        }
//    }
    //    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
//        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) holder;
//        final String model = modelList.get(position);
//        viewHolder.itemView.setTextDirection(Integer.parseInt(model));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        View mview;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mview = itemView;
//
//        }
//
//        public void setDetails(Context ctx, String title, String image, String author) {
//            TextView mTitle = mview.findViewById(R.id.rTitleMG);
//            TextView mAuthor = mview.findViewById(R.id.rNameAuthor);
//            ImageView mImage = mview.findViewById(R.id.rImage);
//            mTitle.setText(title);
//            mAuthor.setText(author);
//            Picasso.get().load(image).into(mImage);
//        }
//
//
//    }
}
