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

        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_row_item, viewGroup, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {



            viewHolder.name.setText(list.get(i).getName()+"");
            viewHolder.price.setText(list.get(i).getPrice()+"");
            if(list.get(i).getImage().length()<6) {
                viewHolder.imageView.setImageResource(R.drawable.ic_launcher_foreground);

            }
            else{

                Picasso.with(viewHolder.imageView.getContext())
                        .load(list.get(i).getImage()).placeholder(R.drawable.ic_launcher_foreground).into(viewHolder.imageView);

            }


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
        TextView name;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_pic);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);

        }
    }
}
