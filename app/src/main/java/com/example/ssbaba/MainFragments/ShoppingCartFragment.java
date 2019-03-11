package com.example.ssbaba.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ssbaba.R;
import com.example.ssbaba.ShoppingCartFragmentAdapter;
import com.example.ssbaba.categoryItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShoppingCartFragment extends android.support.v4.app.Fragment {

        RecyclerView recyclerView;
        ArrayList<categoryItem> arrayList;
        ShoppingCartFragmentAdapter adapter;
        FirebaseDatabase database;
        DatabaseReference userRef,itemRef,cartRef;
        String currentUserId;
        FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        FirebaseApp.initializeApp(view.getContext());
        recyclerView=view.findViewById(R.id.cart_fragment_recycler_view);



        return view;
    }



}
