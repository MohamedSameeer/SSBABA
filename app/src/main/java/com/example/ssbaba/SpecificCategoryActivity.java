package com.example.ssbaba;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SpecificCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<categoryItem> arrayList;
    CategoryActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_category);
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Categories");
        recyclerView = findViewById(R.id.category_recycler_view);
        arrayList = new ArrayList<>();

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


                 Log.e("Specific Category","in");
                /* categoryItem categoryItem=snapshot.getValue(com.example.ssbaba.categoryItem.class);
                 arrayList.add(categoryItem);*/
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e("Specific Category","Error OnCancelled");
            }
        });

        adapter = new CategoryActivityAdapter(arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
