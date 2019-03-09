package com.example.ssbaba.Regestration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.example.ssbaba.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PresenterRegestration {

    private Context context;
    private String email,password;
    private FirebaseAuth mAuth;
    private boolean x ;
    private DatabaseReference userRef;
    private IViewRegestration iViewRegestration;
    public PresenterRegestration(Context context){
        this.context=context;
        mAuth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference().child("Users");
        iViewRegestration=new ActivityRegestration();

    }

    public void creatAccount(EditText email,EditText password) {
        this.email=email.getText().toString().trim();
        this.password=password.getText().toString().trim();
        boolean flag=true;
        if(this.email.trim().isEmpty()){
            email.setError("can't leave this field empty");
            flag=false;
        }
        if(this.email.trim().isEmpty()){
            password.setError("can't be this field empty");
            flag=false;
        }
        if(flag){
            Log.e("Activity Regestration","Done");
            createNewUser();
        }
    }


    public boolean createNewUser() {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String user=mAuth.getCurrentUser().getUid();
                            createUserDatabase(user);
                            Log.e("Presenter Regestration","creating new Account Successful ");

                        }else {
                            Log.e("Presenter Regestration","signInWithEmail:failure", task.getException());
                            x=false;
                        }
                    }
                });
            return x;
    }

    private void createUserDatabase(final String user) {
        userRef.child(user).child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.e("Presenter Regestration","Regestration Comp");
                    iViewRegestration.StartNewActivity();
                }
                else{
                    Log.e("Presenter Regestration","Exciption : "+task.getException());
                }
            }
        });

    }


}
