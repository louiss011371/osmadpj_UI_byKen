package com.example.osmadpj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class PrivacyAndTerms extends AppCompatActivity {

    private Button Back,Back2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacyandterms);

        Back = findViewById(R.id.btn_back);
        Back2 = findViewById(R.id.btn_back2);

        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PrivacyAndTerms.this.finish();
            }
        });

        Back2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PrivacyAndTerms.this.finish();
            }
        });
    }
}
