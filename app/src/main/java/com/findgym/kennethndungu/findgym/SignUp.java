package com.findgym.kennethndungu.findgym;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    EditText EtUsernameSignup,EtFirstnameSignup,EtLastnameSignup,EtEmailSignup,EtPasswordSignup;
    Button BtnSignup;
    String EtFNameSignUpHolder, EtLNameSignUpHolder, EtEmailSignUpHolder, EtUsernameSignUpHolder, EtPasswordSignUpHolder;
    String finalResult;
    String HttpURL = "https://sancarepreparatoryschool.co.ke/FindGym/UserRegistration.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EtFirstnameSignup=(EditText)findViewById(R.id.EtFirstnameSignup);
        EtLastnameSignup=(EditText)findViewById(R.id.EtLastnameSignup);
        EtEmailSignup=(EditText)findViewById(R.id.EtEmailSignup);
        EtUsernameSignup=(EditText)findViewById(R.id.EtUsernameSignup);
        EtPasswordSignup=(EditText)findViewById(R.id.EtPasswordSignup);
        BtnSignup=(Button)findViewById(R.id.BtnSignup);

        BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText){
                    // If EditText is not empty and CheckEditText = True then this block will execute.
                    UserRegisterFunction(EtFNameSignUpHolder,EtLNameSignUpHolder,EtEmailSignUpHolder,
                            EtUsernameSignUpHolder, EtPasswordSignUpHolder);
                }else{
                    // If EditText is empty then this block will execute .
                    Toast.makeText(SignUp.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){
        EtFNameSignUpHolder = EtFirstnameSignup.getText().toString();
        EtLNameSignUpHolder = EtLastnameSignup.getText().toString();
        EtEmailSignUpHolder = EtEmailSignup.getText().toString();
        EtUsernameSignUpHolder=EtUsernameSignup.getText().toString();
        EtPasswordSignUpHolder = EtPasswordSignup.getText().toString();

        if(TextUtils.isEmpty(EtFNameSignUpHolder) || TextUtils.isEmpty(EtLNameSignUpHolder) || TextUtils.isEmpty(EtEmailSignUpHolder)
                || TextUtils.isEmpty(EtUsernameSignUpHolder) ||TextUtils.isEmpty(EtPasswordSignUpHolder))
        {
            CheckEditText = false;
        }
        else {
            CheckEditText = true ;
        }
    }

    public void UserRegisterFunction(final String EtFirstnameSignup, final String EtLastnameSignup, final String EtEmailSignup,
                                     final String EtUsernameSignup, final String EtPasswordSignup) {

        class UserRegisterFunctionClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(SignUp.this, "Just a few seconds..", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(SignUp.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                //names should be the same as POST method declared in UserRegistration.php
                hashMap.put("firstname", params[0]);
                hashMap.put("lastname", params[1]);
                hashMap.put("email", params[2]);
                hashMap.put("username", params[3]);
                hashMap.put("password", params[4]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();
        userRegisterFunctionClass.execute(EtFirstnameSignup,EtLastnameSignup,EtEmailSignup,EtUsernameSignup,EtPasswordSignup);
    }

    //sends user to Login screen
    public void LoginScreen(View view) {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
