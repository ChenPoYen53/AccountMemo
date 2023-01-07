package com.example.accountmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.transition:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new transitionFragment()).commit();
                    break;
                case R.id.statistics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new statisticsFragment()).commit();
                    break;
                case R.id.asset:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new assetFragment()).commit();
                    break;
            }
            return true;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new transitionFragment()).commit();

        Stetho.initializeWithDefaults(this);
    }
}