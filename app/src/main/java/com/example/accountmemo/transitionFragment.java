package com.example.accountmemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.accountmemo.Database.MainData;
import com.example.accountmemo.Database.RoomDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.LongStream;

public class transitionFragment extends Fragment
{
    private static final String TAG = "transitionFragment";
    private static final String INCOME = "Income";
    private static final String EXPENSES = "Expenses";
    private FloatingActionButton btn_add;
    private Intent intent;
    private RoomDB roomDB;
    private MainData mainData;
    private RecyclerView recyclerView;
    private tf_RecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<MainData> mainDataList;
    private TextView incomeM,expenseM,totalM,incomeD,expenseD,totalD;
    private Calendar calendar = Calendar.getInstance();
    private List<MainData> listI = new ArrayList();
    private List<MainData> listE = new ArrayList();
    private List<MainData> listDI = new ArrayList();
    private List<MainData> listDE = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment,container,false);

        roomDB = RoomDB.getInstance(view.getContext());
        mainDataList = roomDB.mainDao().getAll();

        incomeM = view.findViewById(R.id.tf_income_m);
        expenseM = view.findViewById(R.id.tf_expenses_m);
        totalM = view.findViewById(R.id.tf_total_m);
        incomeD = view.findViewById(R.id.tf_income_d);
        expenseD = view.findViewById(R.id.tf_expenses_d);
        totalD = view.findViewById(R.id.tf_total_d);


        Activity activity = (Activity) view.getContext();
        recyclerView = view.findViewById(R.id.tf_recyclerView);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new tf_RecyclerViewAdapter(activity,mainDataList);
        recyclerView.setAdapter(adapter);

        btn_add = view.findViewById(R.id.add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(),addTransition.class);
                startActivity(intent);
            }
        });
        monthInOut();
        DailyInOut();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        monthInOut();
        DailyInOut();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void monthInOut()
    {
        int Y = calendar.get(Calendar.YEAR);
        int M = calendar.get(Calendar.MONTH+1);

        long IncomeTotal = 0;
        long ExpensesTotal = 0;

        mainDataList = roomDB.mainDao().getAll();

        listI = roomDB.mainDao().getMonthIncome(INCOME,Y,M);
        listE = roomDB.mainDao().getMonthExpense(EXPENSES,Y,M);

        for (int i = 0; i < listI.size(); i++)
        {
            IncomeTotal += listI.get(i).getCost();
        }
        Log.d(TAG,"LIST.I.IncomeTotal..."+IncomeTotal);
        for (int i = 0; i < listE.size(); i++)
        {
            ExpensesTotal += listE.get(i).getCost();
        }
        Log.d(TAG,"LIST.I.ExpensesTotal..."+ExpensesTotal);


        incomeM.setText(String.valueOf(IncomeTotal));
        expenseM.setText(String.valueOf(ExpensesTotal));
        totalM.setText(String.valueOf(IncomeTotal-ExpensesTotal));
        adapter.notifyDataSetChanged();
    }
    private void DailyInOut()
    {
        int Y = calendar.get(Calendar.YEAR);
        int M = calendar.get(Calendar.MONTH+1);
        int D = calendar.get(Calendar.DAY_OF_MONTH);

        long IncomeTotal = 0;
        long ExpensesTotal = 0;

        listDI = roomDB.mainDao().getDailyIncome(INCOME,Y,M,D);
        listDE = roomDB.mainDao().getDailyExpense(EXPENSES,Y,M,D);

        for (int i=0;i<listDI.size();i++)
        {
            IncomeTotal += listDI.get(i).getCost();
        }
        for (int i=0;i<listDE.size();i++)
        {
            ExpensesTotal += listDE.get(i).getCost();
        }
        incomeD.setText(String.valueOf(IncomeTotal));
        expenseD.setText(String.valueOf(ExpensesTotal));
        totalD.setText(String.valueOf(IncomeTotal-ExpensesTotal));
        adapter.notifyDataSetChanged();
    }
}
