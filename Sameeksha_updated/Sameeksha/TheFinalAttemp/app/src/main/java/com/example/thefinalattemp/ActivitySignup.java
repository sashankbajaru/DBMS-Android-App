package com.example.thefinalattemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ActivitySignup extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void Onsignup(View view){
        BackgroundWorker asyncTask = new BackgroundWorker(this);

        EditText signUpnameText = (EditText) findViewById(R.id.nameText);
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText signUppasswordText = (EditText) findViewById(R.id.signUppasswordText);
        EditText reTypePassword = (EditText) findViewById(R.id.rePasswordText);
        TextView Errormsg = (TextView) findViewById(R.id.errorMsg);

        Errormsg.setText("");
        String name = signUpnameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = signUppasswordText.getText().toString();
        String rePassword = reTypePassword.getText().toString();
        if(name.equals("")||email.equals("")||password.equals("")||rePassword.equals("")){
            Errormsg.setText("None of the above fields should be empty!");
            return;
        }
        if(!password.equals(rePassword)){
            Errormsg.setText("Password retyped incorrectly!");
            return;
        }

        signUppasswordText.setText("");
        reTypePassword.setText("");

        String type = "signin";
        asyncTask.delegate = this;
        asyncTask.execute(type,name,email,password,rePassword);
    }

    //// Action when don't have an account is clicked in signup page
    public void onAlreadyHaveAnAccount(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void processFinish(String output) {
        if(output.equals("Successfully registered!")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
