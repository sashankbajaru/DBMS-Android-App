package com.example.sameekshaapp;

import android.content.Context;
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

    public BackgroundWorker(Context ctx){ context = ctx; }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String searchKey = params[1];
        String resultString = "";

        if(type.equals("search")){
            String searchURL = "http://192.168.43.161/Sameeksha_app/search.php";
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
        return resultString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String resultString) {
        LinearLayout linearLayout = ((SearchResultsActivity)context).getLinearLayout();

        if(resultString.equals("no results")){
//            code for no results received
            TextView emptyTextView = new TextView(context);
            emptyTextView.setText("");
            linearLayout.addView(emptyTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            emptyTextView.setPadding(0,400,0,0);

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

            for (int i=0;i<jsonArray.length();++i){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String movieId = jsonObject.getString("mid");
                String title = jsonObject.getString("title");
                String yearOfRelease = jsonObject.getString("yearofrelease");
                String img = jsonObject.getString("img");
                String imageUrl = "http://192.168.43.161/Sameeksha_app/img/"+img;
                String rating = jsonObject.getString("rating");

                ImageView imageView = new ImageView(context);
                Picasso.get().load(imageUrl).fit().into(imageView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300,400);
                params.gravity = Gravity.CENTER;
                imageView.setPadding(0,10,0,0);
                imageView.setContentDescription(movieId);
                imageView.setLayoutParams(params);
                linearLayout.addView(imageView);

//                Click on the image event
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String movieId = v.getContentDescription().toString();
                    }
                });

//                code for inserting text related to movie
                TextView titleTextView = new TextView(context);
                titleTextView.setText(title + " ("+ yearOfRelease + ")");
                titleTextView.setTextSize(16);
                titleTextView.setPadding(0,5,0,0);
                titleTextView.setGravity(Gravity.CENTER);
                titleTextView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
                linearLayout.addView(titleTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                TextView ratingTextView = new TextView(context);
                if(rating.equals("null")){
                    ratingTextView.setText("Not yet rated");
                }
                else {
                    ratingTextView.setText("Rating " + rating);
                }
                ratingTextView.setGravity(Gravity.CENTER);
                ratingTextView.setPadding(0,5,0,0);
                ratingTextView.setTextSize(18);
                ratingTextView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
                linearLayout.addView(ratingTextView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
