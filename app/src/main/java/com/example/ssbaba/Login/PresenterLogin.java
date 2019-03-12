package com.example.ssbaba.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ssbaba.MainActivity;
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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.support.constraint.Constraints.TAG;

public class PresenterLogin {

    String sEmail,sPassword;
    Context context;
    FirebaseAuth mAuth;
    boolean x;
    IViewLogin iViewLogin;
    EditText mTxtPassword,mTxtEmail;
    ProgressDialog loading;
    ActivityLogin activityLogin;
    public PresenterLogin(Context context,ProgressDialog loading){
        this.context=context;
        mAuth=FirebaseAuth.getInstance();
        this.loading=loading;
        iViewLogin=new ActivityLogin();
    }


    public void loginWithAccount(EditText email, EditText password) {
        this.mTxtEmail=email;
        this.mTxtPassword=password;
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
            this.loading.setTitle("Sign In");
            this.loading.setMessage("Please Wait....");
            this.loading.setCanceledOnTouchOutside(true);
            this.loading.show();
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
                            loading.dismiss();
                            iViewLogin.StartMainActivity();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            loading.dismiss();
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            switch (errorCode) {

                                case "ERROR_INVALID_CUSTOM_TOKEN":
                                    Toast.makeText(context, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                    Toast.makeText(context, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    Toast.makeText(context, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    mTxtEmail.setError("The email address is badly formatted.");
                                    mTxtEmail.requestFocus();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    mTxtPassword.setError("password is incorrect ");
                                    mTxtPassword.requestFocus();
                                    mTxtPassword.setText("");
                                    break;

                                case "ERROR_USER_MISMATCH":
                                    Toast.makeText(context, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_REQUIRES_RECENT_LOGIN":
                                    Toast.makeText(context, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    Toast.makeText(context, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    mTxtEmail.setError("The email address is already in use by another account.");
                                    mTxtEmail.requestFocus();
                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    Toast.makeText(context, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(context, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_TOKEN_EXPIRED":
                                    Toast.makeText(context, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    Toast.makeText(context, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_USER_TOKEN":
                                    Toast.makeText(context, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    Toast.makeText(context, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    mTxtPassword.setError("The password is invalid it must 6 characters at least");
                                    mTxtPassword.requestFocus();
                                    break;

                            }
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
                loading.dismiss();

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
                            loading.dismiss();
                            iViewLogin.StartMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            loading.dismiss();
                        }

                        // ...
                    }
                });
        Log.e("firebaseAuthWithGoogle",x+"");
    }
}
