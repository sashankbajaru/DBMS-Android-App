package com.example.thefinalattemp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText nameText,passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameText = (EditText) findViewById(R.id.nameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
    }

    public void OnLogin(View view){
        String username = nameText.getText().toString();
        String password = passwordText.getText().toString();
        String type="login";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(username,password,type);


    }
    public void Onsignup(View view){

    }



}
