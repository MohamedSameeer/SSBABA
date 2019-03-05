package com.example.ssbaba;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivityAdapter extends RecyclerView.Adapter<CategoryActivityAdapter.ViewHolder> {
    List<categoryItem> list;
    public CategoryActivityAdapter(List<categoryItem> list){

        this.list=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_specific_category, viewGroup, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            viewHolder.textView.setText(list.get(i).getName());
            viewHolder.textView1.setText(list.get(i).getPrice());
            Picasso.with(viewHolder.imageView.getContext()).load(list.get(i).getImage()).into(viewHolder.imageView);



    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_pic);
            textView=itemView.findViewById(R.id.name);
            textView1=itemView.findViewById(R.id.price);

        }
    }
}
