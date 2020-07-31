package com.example.googlebooksasynctask;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchBook extends AsyncTask<String,Void,String>{

    @SuppressLint("StaticFieldLeak")
    private TextView mTitleText;
    @SuppressLint("StaticFieldLeak")
    private TextView mAuthorText;
    @SuppressLint("StaticFieldLeak")
    private TextView mDescriptionText;

    private String tytul;
    private String autor;
    private String opisKsiazki;
    private String linkDoKsiazki;

    FetchBook(TextView titleText, TextView authorText, TextView descriptionText,
              String tytul, String autor, String opisKsiazki) {
        this.mTitleText = titleText;
        this.mAuthorText = authorText;
        this.mDescriptionText = descriptionText;
        this.tytul = tytul;
        this.autor = autor;
        this.opisKsiazki = opisKsiazki;
    }

    @Override
    protected String doInBackground(String... params) {
        String queryString = params[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";

            final String QUERY_PARAM = "q";
            final String MAX_RESULTS = "maxResults";
            final String PRINT_TYPE = "printType";

            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return null;
            }
            bookJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return bookJSONString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;
            String description = null;
            String bookLink = null;

            while (i < itemsArray.length() || (authors == null && title == null)) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    description = volumeInfo.getString("description");
                    bookLink = volumeInfo.getString("infoLink");
                } catch (Exception e){
                    e.printStackTrace();
                }
                i++;
            }

            if (title != null && authors != null && description != null){
                mTitleText.setText(title);
                mAuthorText.setText(authors);
                mDescriptionText.setText(description);
                tytul = title;
                autor = authors;
                opisKsiazki = description;

            } else {
                mTitleText.setText(R.string.nie_znaleziono);
                mAuthorText.setText("");
                mDescriptionText.setText("");
            }

        } catch (Exception e){
            mTitleText.setText(R.string.nie_znaleziono);
            mAuthorText.setText("");
            mDescriptionText.setText("");
            e.printStackTrace();
        }
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getOpisKsiazki() {
        return opisKsiazki;
    }

    public void setOpisKsiazki(String opisKsiazki) {
        this.opisKsiazki = opisKsiazki;
    }

    public String getLinkDoKsiazki() {
        return linkDoKsiazki;
    }

    public void setLinkDoKsiazki(String linkDoKsiazki) {
        this.linkDoKsiazki = linkDoKsiazki;
    }
}
