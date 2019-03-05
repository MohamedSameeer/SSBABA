package com.example.ssbaba.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ssbaba.R;
import com.example.ssbaba.Regestration.PresenterRegestration;

public class ActivityLogin extends AppCompatActivity {
    EditText email,password;
    Button signIn;
    String sEmail,sPassword;
    PresenterLogin presenterLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intialzation();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatAccount();
            }
        });
    }

    private void intialzation(){
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.sign_in);
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
