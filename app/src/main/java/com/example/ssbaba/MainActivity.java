package com.example.ssbaba;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef ;
    RecyclerView categroiesRecyclerView;
    private static final int PICK_IMG_REQUEST =1 ;
    private static final int SPACING =1 ;
    private static int SPAN_COUNT=4;
    CategoryAdapter adapter;
    ArrayList<Item> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Categories");
        arrayList = new ArrayList<>();
        arrayList.add(new Item(R.drawable.a,"phones"));
        arrayList.add(new Item(R.drawable.b,"tablets"));
        arrayList.add(new Item(R.drawable.c,"Smart Watches"));
        arrayList.add(new Item(R.drawable.d,"Accessories"));

        categroiesRecyclerView = findViewById(R.id.categories);

        adapter = new CategoryAdapter(arrayList, this, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //to click on View and open a new Activity previewing its content
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        categroiesRecyclerView.setLayoutManager(gridLayoutManager);
        categroiesRecyclerView.addItemDecoration(new GridItemDecoration(SPAN_COUNT,SPACING,true));
        categroiesRecyclerView.setAdapter(adapter);

    }


}
