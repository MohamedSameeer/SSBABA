package com.example.ssbaba.Regestration;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PresenterRegestration {

    private Context context;
    private String email,password,userName,visa;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    public PresenterRegestration(String userName, String email, String password
            , String visa, Context context){
        this.userName=userName;
        this.email=email;
        this.password=password;
        this.visa=visa;
        this.context=context;
        mAuth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference().child("Users");
        createNewUser();
    }

    private void createNewUser() {

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
                        }
                    }
                });

    }

    private void createUserDatabase(final String user) {
        userRef.child(user).child("name").setValue(userName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    userRef.child(user).child("visa").setValue(visa).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                userRef.child(user).child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Log.e("Presenter Regestration","Regestration Compelete");
                                            }
                                            else{
                                                Log.e("Presenter Regestration","Exciption : "+task.getException());
                                            }
                                    }
                                });
                            }else{
                                Log.e("Presenter Regestration","Exciption : "+task.getException());
                            }
                        }
                    });
                }else{
                    Log.e("Presenter Regestration","Exciption : "+task.getException());
                }
            }
        });
    }


}
