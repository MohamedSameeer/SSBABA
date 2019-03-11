package com.example.ssbaba.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class wishListFragment extends android.support.v4.app.Fragment {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference wish_list_ref,category_ref;
    String user_id;
    View view;
    ShoppingCartFragmentAdapter adapter;
    List<categoryItem> listOfWish;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_wish_list, container, false);

        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user_id=mAuth.getCurrentUser().getUid();
        Log.e("useridddddd",user_id);

        category_ref=firebaseDatabase.getReference().child("Categories");
        wish_list_ref=firebaseDatabase.getReference().child("wish_list");

        listOfWish=new ArrayList<>();
        getList(user_id);
        RecyclerView recyclerView=view.findViewById(R.id.wish_container);
        adapter=new ShoppingCartFragmentAdapter(listOfWish);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        return view;
    }

    private void getList(String user_id) {
        wish_list_ref.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String id =snapshot.child("id").getValue().toString();
                    String type=snapshot.child("type").getValue().toString();
                    category_ref.child(type).child("List").child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            categoryItem categoryItem = new categoryItem();
                            categoryItem.setColor(snapshot.child("color").getValue().toString());
                            categoryItem.setDescription(snapshot.child("description").getValue().toString());
                            categoryItem.setImage(snapshot.child("image").getValue().toString());
                            categoryItem.setModel(snapshot.child("model").getValue().toString());
                            categoryItem.setName(snapshot.child("name").getValue().toString());
                            categoryItem.setPrice(snapshot.child("price").getValue().toString());
                            categoryItem.setYear(snapshot.child("year").getValue().toString());
                            categoryItem.setId(snapshot.child("id").getValue().toString());
                            categoryItem.setType(snapshot.child("type").getValue().toString());
                            listOfWish.add(categoryItem);
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
