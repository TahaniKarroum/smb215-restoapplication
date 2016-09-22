package com.example.pc.restoapplication.Login;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.restoapplication.MainActivity;
import com.example.pc.restoapplication.R;
import com.example.pc.restoapplication.helper.CommunicationAsyn;
import com.example.pc.restoapplication.helper.PrefUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username;
    EditText password;
    Button loginBtn;
    ProgressDialog nDialog;
    boolean done = false;
    boolean isfinished = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String usernamestr = username.getText() + "";
        String passwordstr = password.getText() + "";
        if (usernamestr.length() == 0 || passwordstr.length() == 0) {
            Toast.makeText(this, "Username and password are required!", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams params = new RequestParams();
            params.put("username", usernamestr);
            params.put("password", passwordstr);
            nDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
            CommunicationAsyn.post("checklogin", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String s = new String(responseBody);
                    Log.i("oneeeee", " oneeee  " + s);
                    isfinished = true;
                    if (Integer.parseInt(s) == 0)
                        done = false;
                    else
                        done = true;
                    nDialog.dismiss();
                    check();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("seconddd", " two");
                    done = false;
                    nDialog.dismiss();
                    check();
                }
            });
        }
        //TODO Remove after test

    }

    public void check() {
        done = true;

        if (done == true) {
            PrefUtils.setLogin(true, getApplicationContext());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        } else
            Toast.makeText(this, "The username and password you entered don't match!", Toast.LENGTH_SHORT).show();
    }

}
