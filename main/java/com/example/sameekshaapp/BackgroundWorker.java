package com.example.sameekshaapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    private Context context;
    private String serverUrl = "http://192.168.43.161/Sameeksha_app/";

    public BackgroundWorker(Context ctx){ context = ctx; }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String resultString = "";

        if(type.equals("search")){
            resultString += "A";
            String searchKey = params[1];
            String searchURL = serverUrl + "search.php";
            try {
                URL url =new URL(searchURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("search_key","UTF-8")+"="+URLEncoder.encode(searchKey,"UTF-8");

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line;

                while ((line = bufferedReader.readLine()) != null){
                    resultString += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (type.equals("display_movie")){
            resultString += "B";
            String movieId = params[1];
            String displayMovieUrl = serverUrl + "display_movie.php?mid="+movieId;

            try {
                URL url = new URL(displayMovieUrl);
                HttpURLConnection  httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line;

                while ((line = bufferedReader.readLine()) != null){
                    resultString += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private void displaySearchResults(String resultString){
        LinearLayout linearLayout = ((SearchResultsActivity) context).getLinearLayout();

        if (resultString.equals("no results")) {
            //            code for no results received
            TextView emptyTextView = new TextView(context);
            emptyTextView.setText("");
            linearLayout.addView(emptyTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            emptyTextView.setPadding(0, 400, 0, 0);

            TextView textView = new TextView(context);
            textView.setText("Sorry your search yielded no results!!!");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            textView.setLayoutParams(params);
            textView.setTextSize(28);
            textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));

            linearLayout.addView(textView);
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(resultString);

            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String movieId = jsonObject.getString("mid");
                String title = jsonObject.getString("title");
                String yearOfRelease = jsonObject.getString("yearofrelease");
                String img = jsonObject.getString("img");
                String imageUrl = "http://192.168.43.161/Sameeksha_app/img/" + img;
                String rating = jsonObject.getString("rating");

                ImageView imageView = new ImageView(context);
                Picasso.get().load(imageUrl).fit().into(imageView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 400);
                params.gravity = Gravity.CENTER;
                imageView.setPadding(0, 10, 0, 0);
                imageView.setContentDescription(movieId);
                imageView.setLayoutParams(params);
                linearLayout.addView(imageView);

                //                Click on the image event
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String movieId = v.getContentDescription().toString();
                        Intent intent = new Intent(context, DisplayMovieActivity.class);
                        intent.putExtra(SearchResultsActivity.MOVIE_ID, movieId);
                        context.startActivity(intent);
                    }
                });

                //                code for inserting text related to movie
                TextView titleTextView = new TextView(context);
                titleTextView.setText(title + " (" + yearOfRelease + ")");
                titleTextView.setTextSize(16);
                titleTextView.setPadding(0, 5, 0, 0);
                titleTextView.setGravity(Gravity.CENTER);
                titleTextView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
                linearLayout.addView(titleTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                TextView ratingTextView = new TextView(context);
                if (rating.equals("null")) {
                    ratingTextView.setText("Not yet rated");
                } else {
                    ratingTextView.setText("Rating " + rating);
                }
                ratingTextView.setGravity(Gravity.CENTER);
                ratingTextView.setPadding(0, 5, 0, 0);
                ratingTextView.setTextSize(18);
                ratingTextView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
                linearLayout.addView(ratingTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addTextView(LinearLayout linearLayout, String title, int textSize, int topPadding, boolean gravityFlag){
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(textSize);
        textView.setPadding(0,topPadding,0,0);
        if(gravityFlag == true){
            textView.setGravity(Gravity.CENTER);
        }
        textView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.holo_blue_dark));
        linearLayout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void displayMovieDetails(String resultString){
        LinearLayout linearLayout = ((DisplayMovieActivity) context).getLinearLayout();
        String resultStringCopy = resultString;

        int movieFrontIndex = resultStringCopy.indexOf('[');
        int movieLastIndex = resultStringCopy.indexOf(']');
        String movieString = resultStringCopy.substring(movieFrontIndex,movieLastIndex+1);
        resultStringCopy = resultStringCopy.substring(movieLastIndex+1);

        int actorFrontIndex = resultStringCopy.indexOf('[');
        int actorLastIndex = resultStringCopy.indexOf(']');
        String actorString = resultStringCopy.substring(actorFrontIndex, actorLastIndex+1);
        resultStringCopy = resultStringCopy.substring(actorLastIndex+1);

        int producerFrontIndex = resultStringCopy.indexOf('[');
        int producerLastIndex = resultStringCopy.indexOf(']');
        String producerString = resultStringCopy.substring(producerFrontIndex, producerLastIndex+1);

        String reviewString = resultStringCopy.substring(producerLastIndex+1);

        try {
            JSONArray movieJsonArray = new JSONArray(movieString);
            JSONObject movieJsonObject = movieJsonArray.getJSONObject(0);
            JSONArray actorJsonArray = new JSONArray(actorString);
            JSONArray producerJsonArray = new JSONArray(producerString);
            JSONArray reviewJsonArray = new JSONArray(reviewString);

            String title = movieJsonObject.getString("title");
            String yearOfRelease = movieJsonObject.getString("yearofrelease");
            String language = movieJsonObject.getString("language");
            String budget = movieJsonObject.getString("budget");
            String boxOffice = movieJsonObject.getString("boxoffice");
            String runningTime = movieJsonObject.getString("runningtime");
            String country = movieJsonObject.getString("country");
            String img = movieJsonObject.getString("img");
            String imageUrl = "http://192.168.43.161/Sameeksha_app/img/" + img;
            String rating = movieJsonObject.getString("rating");
            String genre = movieJsonObject.getString("genre");

//            IMAGE
            ImageView imageView = new ImageView(context);
            Picasso.get().load(imageUrl).fit().into(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(600, 800);
            params.gravity = Gravity.CENTER;
            imageView.setPadding(0, 10, 0, 0);
            imageView.setLayoutParams(params);
            linearLayout.addView(imageView);

//            TITLE
            addTextView(linearLayout, title,20,5,true);

//            RATING
            if(rating.equals("null")){
                addTextView(linearLayout,"Not yet rated",18,2,true);
            }
            else{
                addTextView(linearLayout,"Rating "+rating,18,2,true);
            }

//            YEAR OF RELEASE
            addTextView(linearLayout,"Year of Release "+ yearOfRelease,16,2,true);

//            LANGUAGE
            addTextView(linearLayout,"Language "+language,16,2,true);

//            GENRE
            addTextView(linearLayout,"Genre "+genre,16,2,true);

//            ACTORS
            addTextView(linearLayout,"\t\t\t\t\t\tStarring",18,2,false);

            for (int i=0;i<actorJsonArray.length();++i){
                JSONObject actorJsonObject = actorJsonArray.getJSONObject(i);
                if (actorJsonObject.length() == 0) break;
                String actorFirstName = actorJsonObject.getString("first_name");
                String actorLastName = actorJsonObject.getString("last_name");
                String actorFullName = actorFirstName + " " + actorLastName;

                addTextView(linearLayout,actorFullName,16,1,true);
            }

//            DIRECTOR
            String directorFirstName = movieJsonObject.getString("dir_fname");
            String directorLastName = movieJsonObject.getString("dir_lname");
            String directorFullName = directorFirstName + " " + directorLastName;

            addTextView(linearLayout,"\t\t\t\t\t\tDirector",18,2,false);

            addTextView(linearLayout,directorFullName,16,0,true);

//            SCREENWRITER
            String screenwriterFirstName = movieJsonObject.getString("scr_fname");
            String screenwriterLastName = movieJsonObject.getString("scr_lname");
            String screenwriterFullName = screenwriterFirstName + " " + screenwriterLastName;

            addTextView(linearLayout,"\t\t\t\t\t\tScreenwriter",18,2,false);

            addTextView(linearLayout,screenwriterFullName,16,0,true);

//            CINEMATOGRAPHER
            String cinematographerFirstName = movieJsonObject.getString("cin_fname");
            String cinematographerLastName = movieJsonObject.getString("cin_lname");
            String cinematographerFullName = cinematographerFirstName + " " + cinematographerLastName;

            addTextView(linearLayout,"\t\t\t\t\t\tCinematographer",18,2,false);

            addTextView(linearLayout,cinematographerFullName,16,0,true);


//            MUSIC
            String musicFirstName = movieJsonObject.getString("mus_fname");
            String musicLastName = movieJsonObject.getString("mus_lname");
            String musicFullName = musicFirstName + " " + musicLastName;

            addTextView(linearLayout,"\t\t\t\t\t\tMusic",18,2,false);

            addTextView(linearLayout,musicFullName,16,0,true);

//            PRODUCERS
            addTextView(linearLayout,"\t\t\t\t\t\tProducers",18,2,false);

            for (int i=0;i<producerJsonArray.length();++i){
                JSONObject producerJsonObject = producerJsonArray.getJSONObject(i);
                if(producerJsonObject.length() == 0) break;
                String producerFirstName = producerJsonObject.getString("first_name");
                String producerLastName = producerJsonObject.getString("last_name");
                String producerFullName = producerFirstName + " " + producerLastName;

                addTextView(linearLayout,producerFullName,16,1,true);
            }

//            EMPTY SPACE
            TextView emptyTextView = new TextView(context);
            emptyTextView.setText("");
            linearLayout.addView(emptyTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            emptyTextView.setPadding(0, 200, 0, 0);

//            REVIEWS
            addTextView(linearLayout,"\tReviews",22,0,false);

            for (int i=0;i<reviewJsonArray.length();++i){
                JSONObject reviewJsonObject = reviewJsonArray.getJSONObject(i);
                if(reviewJsonObject.length() == 0) break;
                String reviewRating = reviewJsonObject.getString("rating");
                String review = reviewJsonObject.getString("review");
                String username = reviewJsonObject.getString("uname");

                addTextView(linearLayout,username + "  Rating : "+ reviewRating,16,10,false);
                addTextView(linearLayout,review, 16, 2, false);
            }

            addTextView(linearLayout,"",16,20,false);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String resultString) {

        if (resultString.charAt(0) == 'A') {
            resultString = resultString.substring(1);
            displaySearchResults(resultString);
        }
        else if (resultString.charAt(0) == 'B'){
            resultString = resultString.substring(1);
            displayMovieDetails(resultString);
        }
    }
}
