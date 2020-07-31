package com.example.googlebooksasynctask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class BookActivity extends AppCompatActivity {

    private String tytul;
    private String autor;
    private String opisKsiazki;
    private String linkDoKsiazki;
    private TextView tytulDaneView;
    private TextView autorDaneView;
    private TextView opisDaneView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Maciej Palus, AsyncTask");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Button wyswietlDane = findViewById(R.id.wyswietlDane);
        Button ok = findViewById(R.id.okButton);
        Button zamow = findViewById(R.id.zamow);
        tytulDaneView = findViewById(R.id.tytulDane);
        autorDaneView = findViewById(R.id.autorDane);
        opisDaneView = findViewById(R.id.opisDane);

        Intent loadInfo = getIntent();
        tytul = Objects.requireNonNull(loadInfo.getExtras()).getString("tytul");

        zamow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, OrderActivity.class);

                tytul = (String) tytulDaneView.getText();
                autor = (String) autorDaneView.getText();
                opisKsiazki = (String) opisDaneView.getText();

                intent.putExtra("tytulKsiazki", tytul);
                intent.putExtra("autorKsiazki", autor);
                intent.putExtra("opisKsiazki", opisKsiazki);

                startActivity(intent);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        wyswietlDane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks(v);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void searchBooks(View v) {
        String queryString = tytul;

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            new FetchBook(tytulDaneView, autorDaneView, opisDaneView, tytul, autor, opisKsiazki).execute(queryString);
        }

        else {
            if (queryString.length() == 0) {
                autorDaneView.setText("");
                tytulDaneView.setText(R.string.nie_podano);
            } else {
                autorDaneView.setText("");
                tytulDaneView.setText(R.string.brak_polaczenia);
            }
        }
    }
}
