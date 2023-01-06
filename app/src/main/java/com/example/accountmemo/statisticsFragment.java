package com.example.accountmemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class statisticsFragment extends Fragment
{
    private BottomNavigationView bottomNavigationView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_fragment,container,false);

        bottomNavigationView = view.findViewById(R.id.top_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.expense:
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_statistics,new expensesFragment()).commit();
                        break;
                    case R.id.income:
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_statistics,new incomeFragment()).commit();
                        break;
                }
                return true;
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container_statistics,new expensesFragment()).commit();
        return view;
    }
}
