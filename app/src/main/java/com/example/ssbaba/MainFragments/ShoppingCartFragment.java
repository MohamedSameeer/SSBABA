package com.example.ssbaba.MainFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ssbaba.R;
import com.example.ssbaba.ShoppingCartFragmentAdapter;
import com.example.ssbaba.categoryItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends android.support.v4.app.Fragment {

        RecyclerView recyclerView;
        ArrayList<categoryItem> arrayList;
        ShoppingCartFragmentAdapter adapter;
        FirebaseDatabase database;
        DatabaseReference categoryRef,cartRef;
        String currentUserId;
        FirebaseAuth mAuth;
ArrayList<String> listOfItemId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        FirebaseApp.initializeApp(view.getContext());
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        cartRef=database.getReference().child("cart").child(currentUserId);
        categoryRef=database.getReference().child("Categories");
        recyclerView=view.findViewById(R.id.cart_fragment_recycler_view);
        arrayList=new ArrayList<>();
        getListOfId();
        getListOfItems();
        adapter=new ShoppingCartFragmentAdapter(arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void getListOfItems() {

    }

    private void getListOfId() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    categoryRef.child(snapshot.child("type").getValue().toString())
                            .child("List").child(snapshot.child("name").getValue().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            categoryItem categoryItem = new categoryItem();
                            categoryItem.setColor(dataSnapshot.child("color").getValue().toString());
                            categoryItem.setDescription(dataSnapshot.child("description").getValue().toString());
                            categoryItem.setImage(dataSnapshot.child("image").getValue().toString());
                            categoryItem.setModel(dataSnapshot.child("model").getValue().toString());
                            categoryItem.setName(dataSnapshot.child("name").getValue().toString());
                            categoryItem.setPrice(dataSnapshot.child("price").getValue().toString());
                            categoryItem.setYear(dataSnapshot.child("year").getValue().toString());
                            categoryItem.setId(dataSnapshot.child("id").getValue().toString());
                            categoryItem.setType(dataSnapshot.child("type").getValue().toString());
                            arrayList.add(categoryItem);
                            Log.e("",categoryItem.getColor());

                            adapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
