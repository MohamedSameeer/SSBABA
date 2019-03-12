package com.example.ssbaba.getUserInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ssbaba.MainActivity;
import com.example.ssbaba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.internal.Util;

public class getUserInfoActivity extends Activity {

    EditText firstNameEt,lastNameEt;
    Button done;
    String firstName,lastName,currentUser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_info);
        FirebaseApp.initializeApp(this);
        context=getBaseContext();
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        userRef=database.getReference().child("Users");
        intializeFields();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateAndSendToFirebase();

            }
        });

    }
    private static Context getContext(){
        return context;
    }
    private void intializeFields() {
        firstNameEt=findViewById(R.id.first_name_et);
        lastNameEt=findViewById(R.id.last_name_et);
        done=findViewById(R.id.done);
    }

    private void validateAndSendToFirebase(){
        firstName=firstNameEt.getText().toString();
        lastName=lastNameEt.getText().toString();
        if (firstName.isEmpty()){
            firstNameEt.requestFocus();
            firstNameEt.setError("you can't leave this field empty");
        }
        else if(lastName.isEmpty()){

            lastNameEt.requestFocus();
            lastNameEt.setError("you can't leave this field empty");
        }
        else{

            userRef.child(currentUser).child("first_name").setValue(firstName).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                    userRef.child(currentUser).child("last_name").setValue(lastName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Intent i = new Intent(getContext(), MainActivity.class );
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();

                            }
                        }
                    });

                }
                }
            });



        }
    };

}
