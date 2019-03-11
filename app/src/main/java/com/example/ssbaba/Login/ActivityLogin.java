package com.example.ssbaba.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ssbaba.MainActivity;
import com.example.ssbaba.R;
import com.example.ssbaba.Regestration.ActivityRegestration;
import com.example.ssbaba.Regestration.PresenterRegestration;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

public class ActivityLogin extends AppCompatActivity implements IViewLogin {
    EditText email,password;
    Button signIn;
    TextView dontHaveAcc;
    SignInButton signWithGoodleAccount;
    String sEmail,sPassword;
    PresenterLogin presenterLogin;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    final int RC_SIGN_IN =0;
    static Context context;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intialzation();
        context=getBaseContext();
        presenterLogin=new PresenterLogin(getContext(),loading);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenterLogin.loginWithAccount(email,password);

            }
        });
        signWithGoodleAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                Intent intent=mGoogleSignInClient.getSignInIntent();
                loading.setTitle("Sign In");
                loading.setMessage("Please Wait....");
                loading.setCanceledOnTouchOutside(true);
                loading.show();
                startActivityForResult(intent,RC_SIGN_IN);


            }
        });
        dontHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityLogin.this, ActivityRegestration.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        presenterLogin.signInWithGoogleAccount(requestCode,RC_SIGN_IN,data);
    }

    private void intialzation(){
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.sign_in);
        dontHaveAcc=findViewById(R.id.dont_have_acc);
        signWithGoodleAccount=findViewById(R.id.sign_in_with_google);
        loading=new ProgressDialog(this);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

    }


    private static Context getContext(){
       return context;
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

    @Override
    public void StartMainActivity() {
        Intent i=new Intent(ActivityLogin.getContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityLogin.getContext().startActivity(i);
        finish();
    }
}
