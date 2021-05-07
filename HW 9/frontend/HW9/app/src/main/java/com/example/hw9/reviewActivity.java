package com.example.hw9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class reviewActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String t1 = intent.getStringExtra("txt1");
        String t2 = intent.getStringExtra("txt2");
        String t3 = intent.getStringExtra("txt3");

        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.review_activity);

        TextView t = findViewById(R.id.reviewText2);
        t.setText(t1);

        t = findViewById(R.id.reviewText1);
        t.setText(t2);

        t = findViewById(R.id.reviewText3);
        t.setText(t3);
    }
}
