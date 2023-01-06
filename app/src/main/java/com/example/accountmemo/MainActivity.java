package com.example.accountmemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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