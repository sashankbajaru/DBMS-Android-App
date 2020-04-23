package com.example.thefinalattemp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;

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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
    Context context;
    AlertDialog alertDialog;
    public AsyncResponse delegate = null;
    private String username,movieIdGlobal,movieNameGlobal;
    private String serverUrl = "http://192.168.43.31/Sameeksha_android/";

    BackgroundWorker(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String msg = "";
        String resultString="";
        if(type.equals("login")){
            //resultString = "login";
            username = params[1];
            String password = params[2];
            String login_url = "http://192.168.43.31/Sameeksha_android/getAns.php";

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream  = httpURLConnection.getInputStream();
                BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                //String result = "";
                String line;
                int cnt = 0;
                while((line = bufferedReader.readLine())!=null){
                    if(cnt==0) resultString += line;
                    cnt+=1;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return resultString;
            }
            catch(MalformedURLException e)
            {
                msg = e.toString();
                e.printStackTrace();
            }
            catch(IOException e){
                msg = e.toString();
                e.printStackTrace();
            }
        }
        else if(type=="signin"){
            //resultString = "signin";
            username = params[1];
            String email = params[2];
            String password = params[3];
            String rePassword = params[4];
            String signup_url = "http://192.168.43.31/Sameeksha_android/signup.php";

            try {
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("rePassword","UTF-8")+"="+URLEncoder.encode(rePassword,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream  = httpURLConnection.getInputStream();
                BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                //String result = "";
                String line;
                int cnt = 0;
                while((line = bufferedReader.readLine())!=null){
                    if(cnt==0) resultString += line;
                    cnt+=1;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return resultString;
            }
            catch(MalformedURLException e) {
                msg = e.toString();
                e.printStackTrace();
            } catch(IOException e){
                msg = e.toString();
                e.printStackTrace();
            }
        }
        else if(type.equals("search")){
            resultString += "A";
            String searchKey = params[1];
            username = params[2];
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
            movieIdGlobal = movieId;
            username = params[2];
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
        else if (type.equals("post_review")){
            //resultString = "login";
            resultString+="P";
            username = params[1];
            String movieId = params[2];
            String ratingStr = params[3];
            String review = params[4];
            String login_url = "http://192.168.43.31/Sameeksha_android/post_review.php";

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("midforreview","UTF-8")+"="+URLEncoder.encode(movieId,"UTF-8")+"&"+URLEncoder.encode("rating","UTF-8")+"="+URLEncoder.encode(ratingStr,"UTF-8")+"&"
                        +URLEncoder.encode("review","UTF-8")+"="+URLEncoder.encode(review,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream  = httpURLConnection.getInputStream();
                BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                //String result = "";
                String line;
                int cnt = 0;
                while((line = bufferedReader.readLine())!=null){
                    resultString += line;
                    cnt+=1;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return resultString;
            }
            catch(MalformedURLException e)
            {
                msg = e.toString();
                e.printStackTrace();
            }
            catch(IOException e){
                msg = e.toString();
                e.printStackTrace();
            }
        }
        return resultString;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        String msg = "Hello Android!";

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
                String imageUrl = "http://192.168.43.31/Sameeksha_android/img/" + img;
                String rating = jsonObject.getString("rating");

                ImageView imageView = new ImageView(context);
                Picasso.get().load(imageUrl).fit().into(imageView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(600, 800);
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
                        intent.putExtra("username",username);
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
        textView.setTextColor(ContextCompat.getColor(context,android.R.color.white));
        linearLayout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void AddPadding(LinearLayout linearLayout,int padding){
        TextView textView = new TextView(context);
        textView.setWidth(200);
        textView.setHeight(padding);
        textView.setBackgroundColor(Color.parseColor("#1E1E1E"));
        linearLayout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void AddDividers(LinearLayout linearLayout,int height){
        TextView textView = new TextView(context);
        textView.setWidth(200);
        textView.setHeight(height);
        textView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.darker_gray));
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
            movieNameGlobal = title;
            String yearOfRelease = movieJsonObject.getString("yearofrelease");
            String language = movieJsonObject.getString("language");
            String budget = movieJsonObject.getString("budget");
            String boxOffice = movieJsonObject.getString("boxoffice");
            String runningTime = movieJsonObject.getString("runningtime");
            String country = movieJsonObject.getString("country");
            String img = movieJsonObject.getString("img");
            String imageUrl = "http://192.168.43.31/Sameeksha_android/img/" + img;
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

            TextView textView0 = new TextView(context);
            textView0.setWidth(200);
            textView0.setHeight(20);
            textView0.setBackgroundColor(Color.parseColor("#1E1E1E"));
            linearLayout.addView(textView0, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(context);
            textView.setWidth(200);
            textView.setHeight(1);
            textView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.darker_gray));
            linearLayout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//            TITLE
            addTextView(linearLayout, title,23,5,true);

//            RATING
            if(rating.equals("null")){
                addTextView(linearLayout,"Not yet rated",18,2,true);
            }
            else{
                addTextView(linearLayout,"Rating: "+rating,18,2,true);
            }

//            YEAR OF RELEASE
            addTextView(linearLayout,"Year of Release: "+ yearOfRelease,16,2,true);

//            LANGUAGE
            addTextView(linearLayout,"Language: "+language,16,2,true);

//            GENRE
            addTextView(linearLayout,"Genre: "+genre,16,2,true);

//          ADDING PADDING AND DIVIDERS

            AddPadding(linearLayout,20);
            AddDividers(linearLayout,1);


//            ACTORS
            addTextView(linearLayout,"\t\tStarring",18,30,false);

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

            addTextView(linearLayout,"\t\tDirector",18,20,false);

            addTextView(linearLayout,directorFullName,16,0,true);

//            SCREENWRITER
            String screenwriterFirstName = movieJsonObject.getString("scr_fname");
            String screenwriterLastName = movieJsonObject.getString("scr_lname");
            String screenwriterFullName = screenwriterFirstName + " " + screenwriterLastName;

            addTextView(linearLayout,"\t\tScreenwriter",18,20,false);

            addTextView(linearLayout,screenwriterFullName,16,0,true);

//            CINEMATOGRAPHER
            String cinematographerFirstName = movieJsonObject.getString("cin_fname");
            String cinematographerLastName = movieJsonObject.getString("cin_lname");
            String cinematographerFullName = cinematographerFirstName + " " + cinematographerLastName;

            addTextView(linearLayout,"\t\tCinematographer",18,20,false);

            addTextView(linearLayout,cinematographerFullName,16,0,true);


//            MUSIC
            String musicFirstName = movieJsonObject.getString("mus_fname");
            String musicLastName = movieJsonObject.getString("mus_lname");
            String musicFullName = musicFirstName + " " + musicLastName;

            addTextView(linearLayout,"\t\tMusic",18,20,false);

            addTextView(linearLayout,musicFullName,16,0,true);

//            PRODUCERS
            addTextView(linearLayout,"\t\tProducers",18,20,false);

            for (int i=0;i<producerJsonArray.length();++i){
                JSONObject producerJsonObject = producerJsonArray.getJSONObject(i);
                if(producerJsonObject.length() == 0) break;
                String producerFirstName = producerJsonObject.getString("first_name");
                String producerLastName = producerJsonObject.getString("last_name");
                String producerFullName = producerFirstName + " " + producerLastName;

                addTextView(linearLayout,producerFullName,16,1,true);
            }
            AddPadding(linearLayout,40);
            AddDividers(linearLayout,1);

//           RATING SPACE
            SpannableString content = new SpannableString("RATE THIS MOVIE");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            TextView RatingTextView = new TextView(context);
            RatingTextView.setText(content);
            RatingTextView.setTextSize(24);
            RatingTextView.setGravity(Gravity.CENTER);
            RatingTextView.setTextColor(ContextCompat.getColor(context,android.R.color.darker_gray));
            linearLayout.addView(RatingTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            RatingTextView.setPadding(0, 80, 0, 80);

            AddDividers(linearLayout,1);
            AddPadding(linearLayout,30);

            RatingTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String movieId = movieIdGlobal;

                    Intent intent = new Intent(context, PostReviewActivity.class);
                    intent.putExtra("movieId",movieId);
                    intent.putExtra("moviename", movieNameGlobal);
                    intent.putExtra("username",username);
                    context.startActivity(intent);
                }
            });

//            REVIEWS
            addTextView(linearLayout,"\tReviews",22,0,false);

            for (int i=0;i<reviewJsonArray.length();++i){
                JSONObject reviewJsonObject = reviewJsonArray.getJSONObject(i);
                if(reviewJsonObject.length() == 0) break;
                String reviewRating = reviewJsonObject.getString("rating");
                String review = reviewJsonObject.getString("review");
                String username = reviewJsonObject.getString("uname");

                addTextView(linearLayout,"\t"+username + "  Rating : "+ reviewRating,16,25,false);
                addTextView(linearLayout,"      "+review,16,2,false);
            }

            addTextView(linearLayout,"",16,20,false);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onPostExecute(String resultString) {
        if(resultString.length()==0){
            alertDialog.setMessage("Failed to connect!");
            alertDialog.show();
        }

        else if (resultString.charAt(0) == 'A') {
            resultString = resultString.substring(1);
            displaySearchResults(resultString);
        }
        else if (resultString.charAt(0) == 'B'){
            resultString = resultString.substring(1);
            displayMovieDetails(resultString);
        }
        else if(resultString.charAt(0) == 'P'){
            alertDialog.setTitle("Review Status");
            resultString = resultString.substring(1);
            alertDialog.setMessage(resultString);
            alertDialog.show();
        }
        else {
            alertDialog.setMessage(resultString);
            alertDialog.show();
            delegate.processFinish(resultString);
        }

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
