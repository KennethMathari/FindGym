package com.findgym.kennethndungu.findgym;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.HashMap;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    EditText EtUsernameLogin,EtPasswordLogin;
    Button BtnLoginLogin;
    String EtUsernameLoginHolder,EtPasswordLoginHolder;
    String finalResult;
    String HttpURL = "https://sancarepreparatoryschool.co.ke/FindGym/UserLogin.php";
    Boolean CheckEditText ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EtUsernameLogin=(EditText)findViewById(R.id.EtUsernameLogin);
        EtPasswordLogin=(EditText)findViewById(R.id.EtPasswordLogin);
        BtnLoginLogin=(Button)findViewById(R.id.BtnLoginLogin);

        BtnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText){
                    UserLoginFunction(EtUsernameLoginHolder,EtPasswordLoginHolder );
                }else {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){
        EtUsernameLoginHolder=EtUsernameLogin.getText().toString();
        EtPasswordLoginHolder=EtPasswordLogin.getText().toString();

        if (TextUtils.isEmpty(EtUsernameLoginHolder) || TextUtils.isEmpty(EtPasswordLoginHolder)){
            CheckEditText=false;

        }else {
            CheckEditText=true;

        }
    }

    public void UserLoginFunction(final String EtUsernameLogin, final String EtPasswordLogin){
        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=progressDialog.show(MainActivity.this,"Just a few seconds..",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                if (httpResponseMsg.equalsIgnoreCase("Login Successful")){
                    finish();
                    Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                    intent.putExtra(UserName,EtUsernameLogin);
                    startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("username",params[0]);
                hashMap.put("password",params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(EtUsernameLogin,EtPasswordLogin);

    }

    //sends the user to the signup screen.
    public void SignupScreen(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
