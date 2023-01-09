package com.example.accountmemo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.LongStream;

public class transitionFragment extends Fragment
{
    private static final String TAG = "transitionFragment";
    private static final String INCOME = "Income";
    private static final String EXPENSES = "Expenses";
    private static final String DATEPICKER = "datePicker";
    private Dialog dialog ;
    private MonthPicker monthPicker;
    private FloatingActionButton btn_add , btn_setting;
    private Button btn_left,btn_right;
    private TextView txv;
    private Intent intent;
    private RoomDB roomDB;
    private RecyclerView recyclerView;
    private tf_RecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<MainData> mainDataList;
    private TextView incomeM,expenseM,totalM,incomeD,expenseD,totalD;
    private final Calendar calendar = Calendar.getInstance();
    private List<MainData> listI = new ArrayList<>();
    private List<MainData> listE = new ArrayList<>();
    private List<MainData> listDI = new ArrayList<>();
    private List<MainData> listDE = new ArrayList<>();

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

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.datepickerdialog);
        monthPicker = (MonthPicker) dialog.findViewById(R.id.month_picker);

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
        /*btn_setting = view.findViewById(R.id.setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ChangeLan(view.getContext());
            }
        });*/
        int M = calendar.get(Calendar.MONTH);
        int MM = M+1;
        Log.d(TAG,"M,MM..."+M+","+MM);
        /*txv = view.findViewById(R.id.tf_txv);
        String time = String.valueOf(calendar.get(Calendar.YEAR))+"."+String.valueOf(MM);
        txv.setText(time);
        txv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePick(view);
            }
        });*/

        monthInOut();
        DailyInOut();
        return view;
    }
    //Date Picker (Year,Month)
    private void datePick(View view)
    {
        btn_left = dialog.findViewById(R.id.btn_left);
        btn_right = dialog.findViewById(R.id.btn_right);

        dialog.setCancelable(true);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = String.valueOf(monthPicker.getYear())+"."+String.valueOf(monthPicker.getMonth() + 1) ;
                txv.setText(text);
                dialog.dismiss();

                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(DATEPICKER,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("year",monthPicker.getYear());
                editor.putInt("month",monthPicker.getMonth()+1);
                editor.commit();

                //adapter.notifyDataSetChanged();
                //recyclerView.setAdapter(adapter);
            }
        });
        dialog.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        monthInOut();
        DailyInOut();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    //Count Monthly Income/Expenses
    private void monthInOut()
    {
        int Y = calendar.get(Calendar.YEAR);
        int M = calendar.get(Calendar.MONTH);
        int MM = M+1;

        long IncomeTotal = 0;
        long ExpensesTotal = 0;

        mainDataList = roomDB.mainDao().getAll();

        listI = roomDB.mainDao().getMonthIncome(INCOME,Y,MM);
        listE = roomDB.mainDao().getMonthExpense(EXPENSES,Y,MM);

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

    //Count Daily Income/Expenses
    private void DailyInOut()
    {
        int Y = calendar.get(Calendar.YEAR);
        int M = calendar.get(Calendar.MONTH);
        int MM = M+1;
        int D = calendar.get(Calendar.DAY_OF_MONTH);

        long IncomeTotal = 0;
        long ExpensesTotal = 0;

        listDI = roomDB.mainDao().getDailyIncome(INCOME,Y,MM,D);
        listDE = roomDB.mainDao().getDailyExpense(EXPENSES,Y,MM,D);

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
    /*private void ChangeLan(Context context)
    {
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(Locale.TRADITIONAL_CHINESE);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
        }else {
            configuration.setLocale(Locale.TRADITIONAL_CHINESE);
        }
        context = context.createConfigurationContext(configuration);
        Locale.setDefault(Locale.getDefault());
    }*/
}
