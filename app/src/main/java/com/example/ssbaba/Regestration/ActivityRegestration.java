package com.example.ssbaba.Regestration;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ssbaba.R;
import com.google.firebase.FirebaseApp;

public class ActivityRegestration extends AppCompatActivity {

    EditText email,password,name,visa;
    String sEmail,sPassword,sName,sVisa;
    PresenterRegestration presenterRegestration;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);
        FirebaseApp.initializeApp(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        intialzation();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatAccount();
            }
        });
    }


    private void intialzation(){
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        name=findViewById(R.id.user_name);
        visa=findViewById(R.id.visa);
        signUp=findViewById(R.id.sign_up);
    }


    private void creatAccount() {
        sEmail=email.getText().toString().trim();
        sPassword=password.getText().toString().trim();
        sName=name.getText().toString().trim();
        sVisa=visa.getText().toString().trim();
        boolean flag=true;
        if(sEmail.trim().isEmpty()){
            email.setError("can't leave this field empty");
            flag=false;
        }
        if(sName.trim().isEmpty()){
            name.setError("can't be this field empty");
            flag=false;
        }
        if(sPassword.trim().isEmpty()){
            password.setError("can't be this field empty");
            flag=false;
        }
        if(sVisa.trim().isEmpty()){
            visa.setError("can't be this field empty");
            flag=false;
        }
        if(flag){
            Log.e("Activity Regestration","Done");
            presenterRegestration=new PresenterRegestration(sName, sEmail, sPassword, sVisa,getApplicationContext());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email",sEmail);
        outState.putString("password",sPassword);
        outState.putString("name",sName);
        outState.putString("visa",sVisa);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        email.setText(savedInstanceState.getString("email"));
        password.setText(savedInstanceState.getString("password"));
        name.setText(savedInstanceState.getString("name"));
        visa.setText(savedInstanceState.getString("visa"));

    }
}
