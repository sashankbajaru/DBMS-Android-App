package com.example.thefinalattemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class SearchResultsActivity extends AppCompatActivity {
    public final static String MOVIE_ID = "com.example.sameekshaapp.MOVIE_ID";
    private LinearLayout linearLayout;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        String searchKey = intent.getStringExtra(Main2Activity.SEARCH_KEY);
        username = intent.getStringExtra("username");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("search",searchKey,username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }
}