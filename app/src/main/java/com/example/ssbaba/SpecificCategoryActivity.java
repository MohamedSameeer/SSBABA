package com.example.ssbaba;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ssbaba.Items.ItemActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpecificCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,wish_list;
    ArrayList<categoryItem> arrayList;
    CategoryActivityAdapter adapter;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_category);
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Categories");
        recyclerView = findViewById(R.id.category_recycler_view);
        arrayList = new ArrayList<>();
        i = getIntent().getIntExtra("i",0);
        Log.e("",i+"");
        switch (i){
            case 0: getPhonesData();
                break;
            case 1: getTabletsData();
            break;
            case 2:getWatchesData();
            break;
            case 3:getAccessoriesData();
            break;

        }

        adapter = new CategoryActivityAdapter(arrayList, new CategoryActivityAdapter.OnItemClickListener() {
            @Override
            public void onClick(int i) {

                Intent intent = new Intent(SpecificCategoryActivity.this, ItemActivity.class);

                categoryItem categoryItem=arrayList.get(i);
                intent.putExtra("name",categoryItem.getName());
                intent.putExtra("color",categoryItem.getColor());
                intent.putExtra("description",categoryItem.getDescription());
                intent.putExtra("image",categoryItem.getImage());
                intent.putExtra("model",categoryItem.getModel());
                intent.putExtra("price",categoryItem.getPrice());
                intent.putExtra("year",categoryItem.getYear());

                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new CategoryActivityAdapter.OnItemClickListener() {
            @Override
            public void onClick(int i) {
                arrayList.get(i);
            }
        });


    }

    private void getPhonesData() {
        databaseReference.child("Phones").child("List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    categoryItem categoryItem = new categoryItem();
                    categoryItem.setColor(snapshot.child("color").getValue().toString());
                    categoryItem.setDescription(snapshot.child("description").getValue().toString());
                    categoryItem.setImage(snapshot.child("image").getValue().toString());
                    categoryItem.setModel(snapshot.child("model").getValue().toString());
                    categoryItem.setName(snapshot.child("name").getValue().toString());
                    categoryItem.setPrice(snapshot.child("price").getValue().toString());
                    categoryItem.setYear(snapshot.child("year").getValue().toString());
                    arrayList.add(categoryItem);
                    Log.e("",categoryItem.getColor());

                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getAccessoriesData() {
        databaseReference.child("Accessories").child("List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    categoryItem categoryItem = new categoryItem();
                    categoryItem.setColor(snapshot.child("color").getValue().toString());
                    categoryItem.setDescription(snapshot.child("description").getValue().toString());
                    categoryItem.setImage(snapshot.child("image").getValue().toString());
                    categoryItem.setModel(snapshot.child("model").getValue().toString());
                    categoryItem.setName(snapshot.child("name").getValue().toString());
                    categoryItem.setPrice(snapshot.child("price").getValue().toString());
                    categoryItem.setYear(snapshot.child("year").getValue().toString());
                    arrayList.add(categoryItem);
                    Log.e("",categoryItem.getColor());
                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getTabletsData() {
        databaseReference.child("Tablet").child("List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    categoryItem categoryItem = new categoryItem();
                    categoryItem.setColor(snapshot.child("color").getValue().toString());
                    categoryItem.setDescription(snapshot.child("description").getValue().toString());
                    categoryItem.setImage(snapshot.child("image").getValue().toString());
                    categoryItem.setModel(snapshot.child("model").getValue().toString());
                    categoryItem.setName(snapshot.child("name").getValue().toString());
                    categoryItem.setPrice(snapshot.child("price").getValue().toString());
                    categoryItem.setYear(snapshot.child("year").getValue().toString());
                    arrayList.add(categoryItem);
                    Log.e("",categoryItem.getColor());

                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getWatchesData() {
        databaseReference.child("SmartWatches").child("List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    categoryItem categoryItem = new categoryItem();
                    if ((snapshot.hasChild("color"))){
                    categoryItem.setColor(snapshot.child("color").getValue().toString());}

                    if((snapshot.hasChild("description"))){
                    categoryItem.setDescription(snapshot.child("description").getValue().toString());}

                    if ((snapshot.hasChild("image"))){
                    categoryItem.setImage(snapshot.child("image").getValue().toString());}

                    if ((snapshot.hasChild("model"))){
                    categoryItem.setModel(snapshot.child("model").getValue().toString());}

                    if ((snapshot.hasChild("name"))){
                    categoryItem.setName(snapshot.child("name").getValue().toString());}

                    if ((snapshot.hasChild("price"))){
                    categoryItem.setPrice(snapshot.child("price").getValue().toString());}

                    if ((snapshot.hasChild("year"))){
                    categoryItem.setYear(snapshot.child("year").getValue().toString());}


                    arrayList.add(categoryItem);
                    Log.e("",categoryItem.getColor());

                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
