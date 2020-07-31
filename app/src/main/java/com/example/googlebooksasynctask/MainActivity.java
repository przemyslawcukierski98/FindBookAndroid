package com.example.googlebooksasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mBookInput;
    private Button mButton;
    private Button mViewInfo;

    String tytul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("xxx, AsyncTask");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.wpiszTytulKsiazki);
        mButton = findViewById(R.id.wyszukajKsiazke);
        mViewInfo = findViewById(R.id.informacjeOMnie);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tytul = mBookInput.getText().toString();

                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                intent.putExtra("tytul", tytul);
                startActivity(intent);
            }
        });

        mViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        InformationDialog informationDialog = new InformationDialog();
        informationDialog.show(getSupportFragmentManager(), "information dialog");
    }
}