package com.insbaixcamp.asyncjson;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        TextView tv1 = findViewById(R.id.tvLang);
        String lang = "";
        if (String.valueOf(getResources().getConfiguration().locale).equals("es_ES")){
            lang = "Español";
        }else if (String.valueOf(getResources().getConfiguration().locale).equals("ca_ES")){
            lang = "Català";
        }else{
            lang = "English";
        }
        tv1.setText(getString(R.string.lang, lang));


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
