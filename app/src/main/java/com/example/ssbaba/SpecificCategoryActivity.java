package com.example.ssbaba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ssbaba.Items.ItemActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpecificCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,wish_list_ref,cart_ref;
    ArrayList<categoryItem> arrayList;
    CategoryActivityAdapter adapter;
    FirebaseAuth mAuth;
    String userId,currentItemId;
    int i;
    Toolbar mToolbar;
    private ProgressDialog loadingbar;
    private categoryItem categoryItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_category);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Categories");
        wish_list_ref=firebaseDatabase.getReference().child("wish_list");
        cart_ref=firebaseDatabase.getReference().child("cart");
        recyclerView = findViewById(R.id.category_recycler_view);


        mToolbar = (Toolbar) findViewById(R.id.spceific_toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadingbar=new ProgressDialog(this);
        loadingbar.setTitle("loading");

        if(arrayList==null) {
            loadingbar.show();
        }

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

        adapter = new CategoryActivityAdapter(arrayList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnLikeClickListener(new CategoryActivityAdapter.OnItemClickListener() {
            @Override
            public void onClick(int i, View v) {
                addToWishList(i);


            }
        });
        adapter.setOnCartClickListener(new CategoryActivityAdapter.OnItemClickListener() {
            @Override
            public void onClick(int i, View view) {
                addToCart(i);
            }
        });
        adapter.setOnViewClickListener(new CategoryActivityAdapter.OnItemClickListener() {
            @Override
            public void onClick(int i, View view) {
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


    }

    //Sameeeeeeeeeeeeeeeeeeeeeeeeeeer
    private void addToWishList(int i) {
        String itemId=arrayList.get(i).getId();
        Toast.makeText(SpecificCategoryActivity.this, itemId+"", Toast.LENGTH_SHORT).show();
        wish_list_ref.child(userId).child(itemId).child("value").setValue("true");
        wish_list_ref.child(userId).child(itemId).child("type").setValue(arrayList.get(i).getType());
        wish_list_ref.child(userId).child(itemId).child("id").setValue(arrayList.get(i).getId());



    }

    private void addToCart(int i){

        currentItemId =arrayList.get(i).getId();
        cart_ref.child(userId).child(currentItemId).child("status").setValue("added to cart");
        Log.e("type","1"+arrayList.get(i).getType());
        cart_ref.child(userId).child(currentItemId).child("type").setValue(arrayList.get(i).getType());
        Log.e("type","1"+arrayList.get(i).getType());
        cart_ref.child(userId).child(currentItemId).child("id").setValue(arrayList.get(i).getId());
    }

    private void getPhonesData() {
        databaseReference.child("Phones").child("List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    categoryItem = new categoryItem();
                    categoryItem.setColor(snapshot.child("color").getValue().toString());
                    categoryItem.setDescription(snapshot.child("description").getValue().toString());
                    categoryItem.setImage(snapshot.child("image").getValue().toString());
                    categoryItem.setModel(snapshot.child("model").getValue().toString());
                    categoryItem.setName(snapshot.child("name").getValue().toString());
                    categoryItem.setPrice(snapshot.child("price").getValue().toString());
                    categoryItem.setYear(snapshot.child("year").getValue().toString());
                    categoryItem.setId(snapshot.child("id").getValue().toString());
                    categoryItem.setType(snapshot.child("type").getValue().toString());
                    arrayList.add(categoryItem);
                    Log.e("",categoryItem.getColor());
                    loadingbar.dismiss();
                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingbar.dismiss();
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
                    categoryItem.setType(snapshot.child("type").getValue().toString());
                    categoryItem.setId(snapshot.child("id").getValue().toString());
                    arrayList.add(categoryItem);
                    Log.e("",categoryItem.getColor());
                    adapter.notifyDataSetChanged();
                    loadingbar.dismiss();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingbar.dismiss();

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
                    categoryItem.setType(snapshot.child("type").getValue().toString());
                    categoryItem.setId(snapshot.child("id").getValue().toString());
                    arrayList.add(categoryItem);
                    Log.e("",categoryItem.getColor());
                    loadingbar.dismiss();

                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingbar.dismiss();

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

                    if ((snapshot.hasChild("id"))){
                        categoryItem.setId(snapshot.child("id").getValue().toString());}

                    if ((snapshot.hasChild("type"))){
                        categoryItem.setType(snapshot.child("type").getValue().toString());}

                    arrayList.add(categoryItem);
                    Log.e("",categoryItem.getColor());
                    loadingbar.dismiss();

                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingbar.dismiss();

            }
        });
    }
}
