package com.example.ssbaba.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.EditText;

import com.example.ssbaba.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.support.constraint.Constraints.TAG;

public class PresenterLogin {

    String sEmail,sPassword;
    Context context;
    FirebaseAuth mAuth;
    boolean x;
    IViewLogin iViewLogin;
    ActivityLogin activityLogin;
    public PresenterLogin(Context context){
        this.context=context;
        mAuth=FirebaseAuth.getInstance();
        iViewLogin=new ActivityLogin();
    }


    public void loginWithAccount(EditText email, EditText password) {
        sEmail=email.getText().toString().trim();
        sPassword=password.getText().toString().trim();
        boolean flag=true;
        if(sEmail.trim().isEmpty()){
            email.setError("can't leave this field empty");
            flag=false;
        }
        if(sPassword.trim().isEmpty()){
            password.setError("can't be this field empty");
            flag=false;
        }
        if(flag==true){
            Log.e("Activity Login","Done");
            loginWithMyAccount();
        }
    }
    public void loginWithMyAccount() {
        Log.d("Presenter Login","enter to method");
                mAuth.signInWithEmailAndPassword(sEmail,sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            Log.e("Presenter Login", "signInWithEmail:success");
                            iViewLogin.StartMainActivity();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.e("Presenter Login", "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public  void signInWithGoogleAccount(int requestCode,int RC_SIGN_IN,Intent data){

        if (requestCode == RC_SIGN_IN && data != null) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e("PresenterLogin","Successful Login with Google Account");
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }

            } catch (ApiException e) {
                Log.e(TAG, "Google sign in failed", e);

            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            iViewLogin.StartMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
        Log.e("firebaseAuthWithGoogle",x+"");
    }
}
