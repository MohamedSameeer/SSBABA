package com.example.ssbaba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ssbaba.Login.ActivityLogin;
import com.example.ssbaba.Login.PresenterLogin;
import com.example.ssbaba.Regestration.ActivityRegestration;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import static com.example.ssbaba.Regestration.ActivityRegestration.getContext;

public class Splash extends AppCompatActivity implements IViewSplash {

    Button login,googleAcc;
    TextView signUp;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog loading;
    static Context context;
    PresenterLogin presenterLogin;
    GoogleSignInOptions gso;
    final int RC_SIGN_IN =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        intitalize();
        context=getBaseContext();
        presenterLogin=new PresenterLogin(getContext(),loading);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Splash.this, ActivityLogin.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Splash.this, ActivityRegestration.class);
                startActivity(intent);
            }
        });

        googleAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient = GoogleSignIn.getClient(Splash.getContext(), gso);
                Intent intent=mGoogleSignInClient.getSignInIntent();
                loading.setTitle("Sign In");
                loading.setMessage("Please Wait....");
                loading.setCanceledOnTouchOutside(true);
                loading.show();
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
    }
    private static Context getContext(){
        return context;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        presenterLogin.signInWithGoogleAccount(requestCode,RC_SIGN_IN,data);
    }

    private void intitalize() {
        login=findViewById(R.id.s_in);
        googleAcc=findViewById(R.id.google_acc);
        signUp=findViewById(R.id.s_up);
        loading=new ProgressDialog(this);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

    }
    @Override
    public void StartMainActivity() {
        Intent i=new Intent(Splash.getContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Splash.getContext().startActivity(i);
        finish();
    }

}

