package com.example.ssbaba;

import android.content.Context;
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
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Item> arrayList;
    private Context context;
    private OnClickListener onClickListener;
        public CategoryAdapter( ArrayList<Item> arrayList , Context context , OnClickListener onClickListener)
        {
            this.arrayList=arrayList;
            this.onClickListener=onClickListener;
            this.context=context;

        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new ViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

       // Picasso.with(viewHolder.itemView.getContext()).load(map.get("Image").toString()).into(viewHolder.image);
        viewHolder.text.setText(arrayList.get(i).getText());
        viewHolder.image.setImageResource(arrayList.get(i).getImage());
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (onClickListener != null)
                   onClickListener.onClick(i);

           }
       });
        }

    @Override
    public int getItemCount() {
            if(arrayList==null){
                return 0;
            }

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.card_image);
            text = itemView.findViewById(R.id.card_text);

        }
    }
    public interface OnClickListener {
        void onClick(int i);
    }
}
