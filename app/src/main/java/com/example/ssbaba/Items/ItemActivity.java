package com.example.ssbaba.Items;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssbaba.R;
import com.example.ssbaba.categoryItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ItemActivity extends AppCompatActivity {

    ImageView itemPicture;
    TextView itemName;
    TextView itemPrice;
    TextView itemColor;
    TextView itemDescription;
    TextView itemYear;
    Button addToCart;
    Button addToWishList;
    String name, price, year, model, image, description, color;
    FirebaseAuth mAuth;
    String currentUser;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    boolean likeState;

    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("WishList");

        mToolbar = (Toolbar) findViewById(R.id.item_toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        intializeFields();
        getData();
        Preview();


    }


    private void Preview() {
        if (image.isEmpty()) {
        } else {
            Picasso.with(getApplicationContext())
                    .load(image).placeholder(R.drawable.ic_launcher_foreground).into(itemPicture);
        }
        itemName.setText(name);
        itemPrice.setText(price);
        itemColor.setText(color);
        itemDescription.setText(description);
        itemYear.setText(year);


    }

    private void getData() {
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        year = getIntent().getStringExtra("year");
        model = getIntent().getStringExtra("model");
        image = getIntent().getStringExtra("image");
        description = getIntent().getStringExtra("description");

        color = getIntent().getStringExtra("color");
    }

    private void intializeFields() {
        itemPicture = findViewById(R.id.item_picture);
        itemName = findViewById(R.id.item_name);
        itemPrice = findViewById(R.id.item_price);
        itemColor = findViewById(R.id.item_color);
        itemDescription = findViewById(R.id.description);
        itemYear = findViewById(R.id.year);
        addToCart = findViewById(R.id.add_to_cart_button);
        addToWishList = findViewById(R.id.add_to_wish_list_2);
        likeState = false;
       /* itemToolbar = findViewById(R.id.item_toolbar);
        setSupportActionBar(itemToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(name);
*/
    }


}
