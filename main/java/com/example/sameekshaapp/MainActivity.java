package com.example.sameekshaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    public final static String SEARCH_KEY = "com.example.sameekshaapp.SEARCH_KEY";
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Set the toolbar to function as an action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        setSupportActionBar(toolbar);

        String imageUrl = "http://192.168.43.161/Sameeksha/img/Sameeksha.jpg";
        imageView = (ImageView) findViewById(R.id.sameeksha_logo);
        Picasso.get().load(imageUrl).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Inflate the main menu here in the main activity
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSearch(View view){
        EditText searchKeyEditText = (EditText) findViewById(R.id.search_key);
        String searchKey = searchKeyEditText.getText().toString();
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra(SEARCH_KEY, searchKey);
        startActivity(intent);
    }
}
