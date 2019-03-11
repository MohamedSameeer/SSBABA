package com.example.ssbaba.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ssbaba.Login.ActivityLogin;
import com.example.ssbaba.R;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    //vars
    FirebaseAuth mAuth;
    String currentUser;
    Button logOut;
    TextView userNameTv;
    FirebaseDatabase database;
    DatabaseReference userRef;
    String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseApp.initializeApp(view.getContext());

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        userRef=database.getReference().child("Users");
        userNameTv=view.findViewById(R.id.user_name_tv);
        getUserData();

        //userNameTv.setText(userName);



        logOut=view.findViewById(R.id.logOut);


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent intent=new Intent(getContext(), ActivityLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //finish();


            }
        });



        return view;

    }

    private void getUserData() {

        userRef.child(currentUser).child("first_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().isEmpty()){
                    userName="no name found";

                }
                else{
                userName=dataSnapshot.getValue().toString();
                Log.e("hello",userName);}
                userNameTv.setText(userName+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
