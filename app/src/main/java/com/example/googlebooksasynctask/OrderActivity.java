package com.example.googlebooksasynctask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class OrderActivity extends AppCompatActivity {

    TextView tytulDaneView;
    TextView autorDaneView;
    TextView opisDaneView;
    EditText dataEditText;
    EditText daneEditText;
    Button zamawiaj;
    Button anuluj;
    Button czytaj;

    String tytulKsiazki;
    String autorKsiazki;
    String opisKsiazki;
    String nazwiskoImie;
    String dataZamowienia;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("xxx, AsyncTask");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        zamawiaj = findViewById(R.id.zamawiaj);
        anuluj = findViewById(R.id.anuluj);
        dataEditText = findViewById(R.id.dataZamowienia);
        daneEditText = findViewById(R.id.daneZamowienia);
        tytulDaneView = findViewById(R.id.tytułOpis);
        autorDaneView = findViewById(R.id.autorOpis);
        opisDaneView = findViewById(R.id.opisOpis);

        Intent loadInfo = getIntent();
        tytulKsiazki = Objects.requireNonNull(loadInfo.getExtras()).getString("tytulKsiazki");
        autorKsiazki = Objects.requireNonNull(loadInfo.getExtras()).getString("autorKsiazki");
        opisKsiazki = Objects.requireNonNull(loadInfo.getExtras()).getString("opisKsiazki");

        tytulDaneView.setText(tytulKsiazki);
        autorDaneView.setText(autorKsiazki);
        opisDaneView.setText(opisKsiazki);

        zamawiaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nazwiskoImie = String.valueOf(daneEditText.getText());
                dataZamowienia = String.valueOf(dataEditText.getText());

                openDialog();
            }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, BookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog(tytulKsiazki, autorKsiazki, dataZamowienia, nazwiskoImie);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
}
