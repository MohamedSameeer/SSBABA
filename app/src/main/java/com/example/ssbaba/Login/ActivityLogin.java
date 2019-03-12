package com.example.ssbaba.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    String sEmail,sPassword;
    PresenterLogin presenterLogin;
    static Context context;
    ProgressDialog loading;
    CheckBox showPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intialzation();
        context=getBaseContext();
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showPass.isChecked()){
                    password.setTransformationMethod(null);

                }else
                {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        presenterLogin=new PresenterLogin(getContext(),loading);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenterLogin.loginWithAccount(email,password);

            }
        });

    }


    private void intialzation(){
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.sign_in);
        loading=new ProgressDialog(this);
        showPass=findViewById(R.id.chk_box);
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

    @Override
    public void getException(String exception) {
        email.setError(exception);
        email.requestFocus();

    }
}
