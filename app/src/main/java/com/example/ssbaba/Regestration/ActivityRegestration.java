package com.example.ssbaba.Regestration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ssbaba.Login.ActivityLogin;
import com.example.ssbaba.MainActivity;
import com.example.ssbaba.R;
import com.example.ssbaba.SpecificCategoryActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;

public class ActivityRegestration extends AppCompatActivity implements IViewRegestration {

    EditText email,password;
    String sEmail,sPassword;
    PresenterRegestration presenterRegestration;
    Button signUp;
    ProgressDialog loading;
    public static Context context;
    CheckBox showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);
        FirebaseApp.initializeApp(this);

        context=getBaseContext();
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        intialzation();

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
        presenterRegestration=new PresenterRegestration(getApplicationContext(),loading);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterRegestration.creatAccount(email,password);
            }
        });
    }


    private void intialzation(){
        loading=new ProgressDialog(this);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signUp=findViewById(R.id.sign_up);
        showPass=findViewById(R.id.chk_box);
    }

    public static Context getContext() {
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
    public void StartNewActivity() {
        Log.e("CreatAccount","True");
        Intent intent=new Intent(ActivityRegestration.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityRegestration.getContext().startActivity(intent);
        finish();
    }
}
