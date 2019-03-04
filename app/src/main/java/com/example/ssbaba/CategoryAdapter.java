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

import java.time.LocalDate;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<CategoryItem> list;
    private Context context;
    private View.OnClickListener onClickListener;
        public CategoryAdapter(List<CategoryItem> list , Context context , View.OnClickListener onClickListener)
        {
            this.list=list;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Picasso.with(viewHolder.itemView.getContext()).load(list.get(i).getImageUrl()).into(viewHolder.image);
        viewHolder.text.setText(list.get(i).getText());

    }

    @Override
    public int getItemCount() {
            if(list==null){
                return 0;
            }

        return list.size();
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
        void onClick(int index);
    }
}
