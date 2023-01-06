package com.example.accountmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accountmemo.Database.MainData;
import com.example.accountmemo.Database.RoomDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class addTransition extends AppCompatActivity {
    private static final String TAG = "addTransition";
    private static final String INCOME = "Income";
    private static final String EXPENSES = "Expenses";
    private static final String CASH = "Cash";
    private static final String BANK_ACCOUNT = "Bank Account";
    private static final String CREDIT_CARD = "Credit Card";
    private String Cost_cant_be_Empty ;
    private String Type_cant_be_Empty ;
    private String Choose_one_kind_of_Asset ;
    private String Choose_a_Date ;
    private String Choose_Income_or_Expenses ;
    private Button btn_income,btn_expenses,btn_cash,btn_account,btn_creditCard,btn_save;
    private TextView txv_time;
    private EditText edt_type,edt_cost,edt_note;
    private String Format = "yyyy/MM/dd/E";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar calendar = Calendar.getInstance();
    private int flag1 = 0;
    private int flag2 = 0;
    private int flag3 = 0;
    private int flag4 = 0;
    private int flag5 = 0;
    private RoomDB roomdb;
    private MainData mainData = new MainData();
    private List<MainData> mainDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transition);

        btn_income = findViewById(R.id.btn_addIncome);
        btn_expenses = findViewById(R.id.btn_addExpenses);
        btn_cash = findViewById(R.id.btn_addCash);
        btn_account = findViewById(R.id.btn_addAccount);
        btn_creditCard = findViewById(R.id.btn_addCreditCard);
        btn_save = findViewById(R.id.btn_addSave);
        txv_time = findViewById(R.id.txv_addTime);
        edt_type = findViewById(R.id.edt_addType);
        edt_cost = findViewById(R.id.edt_addCost);
        edt_note = findViewById(R.id.edt_addNote);

        Cost_cant_be_Empty = getResources().getString(R.string.Cost_cant_be_Empty);
        Type_cant_be_Empty = getResources().getString(R.string.Type_cant_be_Empty);
        Choose_one_kind_of_Asset = getResources().getString(R.string.Choose_one_kind_of_Asset);
        Choose_a_Date = getResources().getString(R.string.Choose_a_Date);
        Choose_Income_or_Expenses = getResources().getString(R.string.Choose_Income_or_Expenses);

        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_incomeClick();
            }
        });
        btn_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_expensesClick();
            }
        });
        btn_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cashClick();
            }
        });
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_accountClick();
            }
        });
        btn_creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_creditCardClick();
            }
        });

        txv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePick();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }
    private void datePick()
    {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format,Locale.TAIWAN);
                txv_time.setText(simpleDateFormat.format(calendar.getTime()));

                SharedPreferences sharedPreferences = getSharedPreferences("DATE_cal",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("year",i);
                editor.putInt("month",i1+1);
                editor.putInt("date",i2);
                editor.commit();
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(addTransition.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    private void btn_incomeClick()
    {
        switch (flag1)
        {
            case 0:
                btn_income.setActivated(true);
                btn_expenses.setActivated(false);
                flag1 = 1;
                flag2 = 0;
                break;
            case 1:
                btn_income.setActivated(false);
                flag1 = 0;
                break;
        }
    }
    private void btn_expensesClick()
    {
        switch (flag2)
        {
            case 0:
                btn_expenses.setActivated(true);
                btn_income.setActivated(false);
                flag2 = 1;
                flag1 = 0;
                break;
            case 1:
                btn_expenses.setActivated(false);
                flag2 = 0;
                break;
        }
    }
    private void btn_cashClick()
    {
        switch (flag3)
        {
            case 0:
                btn_cash.setActivated(true);
                btn_account.setActivated(false);
                btn_creditCard.setActivated(false);
                flag3 = 1;
                flag4 = 0;
                flag5 = 0;
                break;
            case 1:
                btn_cash.setActivated(false);
                flag3 = 0;
                break;
        }
    }
    private void btn_accountClick()
    {
        switch (flag4)
        {
            case 0:
                btn_account.setActivated(true);
                btn_cash.setActivated(false);
                btn_creditCard.setActivated(false);
                flag4 = 1;
                flag3 = 0;
                flag5 = 0;
                break;
            case 1:
                btn_account.setActivated(false);
                flag4 = 0;
                break;
        }
    }
    private void btn_creditCardClick()
    {
        switch (flag5)
        {
            case 0:
                btn_creditCard.setActivated(true);
                btn_account.setActivated(false);
                btn_cash.setActivated(false);
                flag3 = 0;
                flag4 = 0;
                flag5 = 1;
                break;
            case 1:
                btn_creditCard.setActivated(false);
                flag5 = 0;
                break;
        }
    }
    private void save()
    {
        if(flag1 == 1 || flag2 == 1)
        {
            if(!txv_time.getText().toString().equals(""))
            {
                if(flag3 == 1 || flag4 == 1 || flag5 == 1)
                {
                    if(!edt_type.getText().toString().equals(""))
                    {
                        if(!edt_cost.getText().toString().equals(""))
                        {
                            Intent intent = new Intent(addTransition.this,MainActivity.class);
                            startActivity(intent);
                            roomdb = RoomDB.getInstance(this);
                            mainDataList = roomdb.mainDao().getAll();
                            if(flag1 == 1 && flag2 == 0)
                            {
                                mainData.setInout(INCOME);
                            }
                            else if(flag1 == 0 && flag2 == 1)
                            {
                                mainData.setInout(EXPENSES);
                            }

                            String asset;
                            if(flag3 == 1 && flag4 == 0 && flag5 == 0)
                            {
                                mainData.setAsset(CASH);
                            }
                            else if(flag3 == 0 && flag4 == 1 && flag5 == 0)
                            {
                                mainData.setAsset(BANK_ACCOUNT);
                            }
                            else if(flag3 == 0 && flag4 == 0 && flag5 == 1)
                            {
                                mainData.setAsset(CREDIT_CARD);
                            }
                            String type,note;
                            type = edt_type.getText().toString();
                            note = edt_note.getText().toString();
                            mainData.setType(type);
                            mainData.setNote(note);
                            long cost;
                            cost = Long.parseLong(edt_cost.getText().toString());
                            mainData.setCost(cost);

                            String day = txv_time.getText().toString();
                            mainData.setDay(day);

                            SharedPreferences sharedPreferences1 = getSharedPreferences("DATE_cal",MODE_PRIVATE);
                            int y2 = sharedPreferences1.getInt("year",0);
                            int m2 = sharedPreferences1.getInt("month",0);
                            int d2 = sharedPreferences1.getInt("date",0);

                            mainData.setYear(y2);
                            mainData.setMonth(m2);
                            mainData.setDate(d2);

                            Log.d(TAG,"DATE_cal..."+y2+m2+d2);

                            roomdb.mainDao().insert(mainData);
                            mainDataList.clear();
                            mainDataList.addAll(roomdb.mainDao().getAll());

                            finish();
                        }
                        else
                            Toast.makeText(this, Cost_cant_be_Empty, Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(this, Type_cant_be_Empty, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, Choose_one_kind_of_Asset, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, Choose_a_Date, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, Choose_Income_or_Expenses, Toast.LENGTH_SHORT).show();
    }
}