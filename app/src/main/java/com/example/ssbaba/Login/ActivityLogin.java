package com.example.ssbaba.Login;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ssbaba.R;
import com.example.ssbaba.Regestration.PresenterRegestration;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

public class ActivityLogin extends AppCompatActivity {
    EditText email,password;
    Button signIn;
    SignInButton signWithGoodleAccount;
    String sEmail,sPassword;
    PresenterLogin presenterLogin;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    PresenterLogin presenterLoginDefault;
    final int RC_SIGN_IN =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intialzation();
        presenterLoginDefault=new PresenterLogin(getApplicationContext());

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatAccount();
            }
        });
        signWithGoodleAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                Intent intent=mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        presenterLoginDefault.signInWithGoogleAccount(requestCode,RC_SIGN_IN,data);
    }

    private void intialzation(){
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.sign_in);
        signWithGoodleAccount=findViewById(R.id.sign_in_with_google);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    private void creatAccount() {
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
            presenterLogin=new PresenterLogin(sEmail,sPassword,getApplicationContext());
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("email",sEmail);
        outState.putString("password",sPassword);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        email.setText(savedInstanceState.getString("email"));
        password.setText(savedInstanceState.getString("password"));
    }

}
