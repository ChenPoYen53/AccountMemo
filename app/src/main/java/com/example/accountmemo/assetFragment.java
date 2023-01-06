package com.example.accountmemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.accountmemo.Database.MainData;
import com.example.accountmemo.Database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class assetFragment extends Fragment
{
    private static final String TAG = "assetFragment";
    private RoomDB roomDB;
    private List<MainData> mainDataList = new ArrayList<>();
    private List<MainData> IncomeList = new ArrayList<>();
    private List<MainData> ExpensesList = new ArrayList<>();
    private List<MainData> CashI = new ArrayList<>();
    private List<MainData> CashE = new ArrayList<>();
    private List<MainData> BankI = new ArrayList<>();
    private List<MainData> BankE = new ArrayList<>();
    private List<MainData> CardI = new ArrayList<>();
    private List<MainData> CardE = new ArrayList<>();
    private TextView assets,liabilities,total,txv_CT,txv_bank,txv_card,txv_ci,txv_ce,txv_bi,txv_be,txv_di,txv_de;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.asset_fragment,container,false);

        roomDB = RoomDB.getInstance(view.getContext());
        mainDataList = roomDB.mainDao().getAll();

        assets = view.findViewById(R.id.txv_asset);
        liabilities = view.findViewById(R.id.txv_liabilities);
        total = view.findViewById(R.id.txv_total);
        txv_CT = view.findViewById(R.id.txv_CT);
        txv_bank = view.findViewById(R.id.txv_bank);
        txv_card = view.findViewById(R.id.txv_card);
        txv_ci = view.findViewById(R.id.Ci);
        txv_ce = view.findViewById(R.id.Ce);
        txv_bi = view.findViewById(R.id.Bi);
        txv_be = view.findViewById(R.id.Be);
        txv_di = view.findViewById(R.id.Di);
        txv_de = view.findViewById(R.id.De);

        GETALT();
        GetCash();
        GetBank();
        GetCard();

        return view;
    }
    private void GETALT()
    {
        IncomeList = roomDB.mainDao().getIncome("Income");
        ExpensesList = roomDB.mainDao().getExpense("Expenses");

        long I = 0 , E = 0 ;

        for(int i = 0 ; i<IncomeList.size() ; i++)
        {
            I += IncomeList.get(i).getCost();
        }
        for (int i = 0 ; i<ExpensesList.size() ; i++)
        {
            E += ExpensesList.get(i).getCost();
        }

        assets.setText(String.valueOf(I));
        liabilities.setText(String.valueOf(E));
        total.setText(String.valueOf(I-E));
    }
    private void GetCash()
    {
        long CI = 0 , CE = 0 , CT = 0 ;

        CashI = roomDB.mainDao().getIncomeByType("Income","Cash");
        CashE = roomDB.mainDao().getExpenseByType("Expenses","Cash");

        for(int i = 0 ; i<CashI.size() ; i++)
        {
            CI += CashI.get(i).getCost();
        }
        for(int i = 0 ; i<CashE.size() ; i++)
        {
            CE += CashE.get(i).getCost();
        }

        CT = CI - CE ;
        txv_ci.setText(String.valueOf(CI));
        txv_ce.setText(String.valueOf(CE));
        txv_CT.setText(String.valueOf(CT));
    }
    private void GetBank()
    {
        long BI = 0 , BE = 0 , BT = 0 ;

        BankI = roomDB.mainDao().getIncomeByType("Income","Bank Account");
        BankE = roomDB.mainDao().getExpenseByType("Expenses","Bank Account");

        for(int i = 0 ; i<BankI.size() ; i++)
        {
            BI += BankI.get(i).getCost();
        }
        for(int i = 0 ; i<BankE.size() ; i++)
        {
            BE += BankE.get(i).getCost();
        }

        BT = BI - BE ;
        txv_bi.setText(String.valueOf(BI));
        txv_be.setText(String.valueOf(BE));
        txv_bank.setText(String.valueOf(BT));
    }
    private void GetCard()
    {
        long DI = 0 , DE = 0 , DT = 0 ;

        CardI = roomDB.mainDao().getIncomeByType("Income","Credit Card");
        CardE = roomDB.mainDao().getExpenseByType("Expenses","Credit Card");

        for(int i = 0 ; i<CardI.size() ; i++)
        {
            DI += CardI.get(i).getCost();
        }
        for(int i = 0 ; i<CardE.size() ; i++)
        {
            DE += CardE.get(i).getCost();
        }

        DT = DI - DE ;
        txv_di.setText(String.valueOf(DI));
        txv_de.setText(String.valueOf(DE));
        txv_card.setText(String.valueOf(DT));
    }


}
