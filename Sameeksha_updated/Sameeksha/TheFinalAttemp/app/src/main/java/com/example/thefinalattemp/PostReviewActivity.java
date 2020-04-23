package com.example.thefinalattemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class PostReviewActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView rateTextView;
    private String username,movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String moviename = intent.getStringExtra("moviename");
        movieId = intent.getStringExtra("movieId");
        username = intent.getStringExtra("username");
        rateTextView = (TextView) findViewById(R.id.RateTextView);
        rateTextView.append(moviename+"?");

        //linearLayout = (LinearLayout) findViewById(R.id.linear_layout_dm);

    }

    public void onPostReview(View view){
        EditText reviewText = (EditText) findViewById(R.id.reviewText);
        String review = reviewText.getText().toString();
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Integer rating = new Integer((int)ratingBar.getRating());
        String rating_str = rating.toString();

        //linearLayout = (LinearLayout) findViewById(R.id.linear_layout_dm);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("post_review",username,movieId,rating_str,review);
    }
}
