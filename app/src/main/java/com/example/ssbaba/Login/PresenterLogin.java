package com.example.ssbaba.Login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PresenterLogin {

    String email,password;
    Context context;
    FirebaseAuth mAuth;
    public PresenterLogin(String email, String password, Context context){
        this.email=email;
        this.password=password;
        this.context=context;
        mAuth=FirebaseAuth.getInstance();
        Log.e("Presenter Login","constractor hey");
        loginWithMyAccount();
    }

    private void loginWithMyAccount() {
        Log.d("Presenter Login","enter to method");
                mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            Log.e("Presenter Login", "signInWithEmail:success");
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.e("Presenter Login", "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }


}
