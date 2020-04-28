package com.example.thefinalattemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class DisplayPersonActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_person);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_dm);

        Intent intent = getIntent();
        String personId = intent.getStringExtra(SearchResultsActivity.PERSON_ID);
        username = intent.getStringExtra("username");

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("display_person",personId,username);
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