package com.example.ssbaba;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoppingCartFragmentAdapter extends RecyclerView.Adapter<ShoppingCartFragmentAdapter.ViewHolder> {
    List<categoryItem>items;

    public ShoppingCartFragmentAdapter(List<categoryItem> items){
        this.items=items;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, viewGroup, false);
        return new ViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.productName.setText(items.get(i).getName());
        viewHolder.productPrice.setText(items.get(i).getPrice());
        if(items.get(i).getImage().length()<6) {
            viewHolder.productImage.setImageResource(R.drawable.ic_launcher_foreground);

        }
        else{

            Picasso.with(viewHolder.productImage.getContext())
                    .load(items.get(i).getImage()).placeholder(R.drawable.ic_launcher_foreground).into(viewHolder.productImage);

        }




    }

    @Override
    public int getItemCount() {
        if(items==null){return 0;}
        else
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.product_image);
            productName=itemView.findViewById(R.id.product_name);
            productPrice= itemView.findViewById(R.id.product_price);

        }
    }
}
