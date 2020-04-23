package com.example.thefinalattemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    private EditText nameText, passwordText, signUpnameText, emailText, signUppasswordText,reTypePassword;
    private Button loginButton;
    private int login_flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameText = (EditText) findViewById(R.id.nameText);
        passwordText = (EditText) findViewById(R.id.signUppasswordText);
        loginButton =(Button) findViewById(R.id.signUpButton);

    }
    // Action when Login button is clicked
    public void OnLogin(View view){
        BackgroundWorker asyncTask = new BackgroundWorker(this);
        TextView errorText = findViewById(R.id.errorText);
        errorText.setText("");
        try {
            String username, password;

            nameText = (EditText) findViewById(R.id.nameText);
            passwordText = (EditText) findViewById(R.id.signUppasswordText);
            username = nameText.getText().toString().trim();
            password = passwordText.getText().toString().trim();

            if(username.equals("")||password.equals("")){
                errorText.setText("Username and Password fields should not be empty");
                return;
            }
            String type = "login";

            asyncTask.delegate = this;
            asyncTask.execute(type,username,password);
            passwordText.setText("");
        }
        catch(Exception e){
            nameText.setText(e.toString());
        }
    }

    // Action when don't have an account is clicked
    public void onNoAccount(View view){
        Intent intent = new Intent(this, ActivitySignup.class);
        startActivity(intent);
        //setContentView(R.layout.activity_signup);
    }


    private void openActivitySignup() {


    }

    private void openHomepage(){
        String username = nameText.getText().toString();

        try {
            Intent intent = new Intent(this, Main2Activity.class);
            nameText = (EditText) findViewById(R.id.nameText);
            String name = nameText.getText().toString();
            intent.putExtra("username",name);
            startActivity(intent);
        }
        catch (Exception e){
            nameText.setText(e.toString());
        }
    }

    @Override
    public void processFinish(String output) {

        if(output.equals("Login Successful!!")){
            login_flag=1;
            openHomepage();
        }
    }
}
