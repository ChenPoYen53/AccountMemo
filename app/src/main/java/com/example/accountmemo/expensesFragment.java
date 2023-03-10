package com.example.accountmemo;

import android.graphics.Color;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class expensesFragment extends Fragment
{
    private static final String TAG = "expensesFragment";
    private static final String EXPENSES = "Expenses";
    private PieChart pieChart;
    private RoomDB roomDB;
    private MainData mainData = new MainData();
    private List<MainData> mainDataList = new ArrayList<>();
    private List<MainData> costList = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();
    private Set<String> set = new HashSet<>();
    private Set<String> exist = new HashSet<>();
    private List<PieEntry> list = new ArrayList<>() ;
    private TextView txv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_expenses_fragment,container,false);

        roomDB = RoomDB.getInstance(view.getContext());
        mainDataList = roomDB.mainDao().getAll();

        pieChart = view.findViewById(R.id.expenses_piechart);
        txv = view.findViewById(R.id.expenses_txv);
        int M = calendar.get(Calendar.MONTH);
        int MM = M+1;
        int Y = calendar.get(Calendar.YEAR);
        String txv_text = String.valueOf(Y)+"/"+String.valueOf(MM);
        txv.setText(txv_text);

        show(view);

        return view;
    }
    private void show(View view)
    {
        pieChart.setUsePercentValues(true);

        pieChart.getDescription().setEnabled(false);

        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        String monthly = view.getResources().getString(R.string.monthly).toUpperCase(Locale.ROOT);
        String expense = view.getResources().getString(R.string.expenses).toUpperCase(Locale.ROOT);
        pieChart.setCenterText(monthly+"\n"+expense);
        pieChart.setEntryLabelColor(Color.BLACK);

        pieChart.setDrawHoleEnabled(true);

        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);

        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(30f);

        pieChart.setTransparentCircleRadius(35f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);

        pieChart.setRotationEnabled(true);

        pieChart.setHighlightPerTapEnabled(true);

        //??????Legend??????
        Legend l = pieChart.getLegend();
        l.setEnabled(false);

        pieChart.setEntryLabelColor(Color.WHITE);

        pieChart.setEntryLabelTextSize(12f);

        roomDB = RoomDB.getInstance(view.getContext());

        int M = calendar.get(Calendar.MONTH);
        int MM = M+1;
        int Y = calendar.get(Calendar.YEAR);

        mainDataList = roomDB.mainDao().getMonthExpense(EXPENSES,Y,MM);

        Log.d(TAG,"TODAY..."+MM);

        ArrayList<String> typeList = new ArrayList<>();
        for(int i=0 ; i<mainDataList.size() ; i++)
        {
            typeList.add(mainDataList.get(i).getType());
            Log.d(TAG,"mainDataList..."+mainDataList.get(i).getType());
        }
        for(int i=0;i<typeList.size();i++) {
            Log.d(TAG, "typeList..." + typeList.get(i));
        }

        Log.d(TAG, "typeList..." + typeList.size());
        Log.d(TAG, "mainDataList..." + mainDataList.size());


        for(String s : typeList)
        {
            if(set.contains(s))
            {
                exist.add(s);
            }
            else
            {
                set.add(s);
            }
        }

        Log.d(TAG,"set..."+set);
        Log.d(TAG,"exist..."+exist);

        for(String s : set)
        {
            costList = roomDB.mainDao().getCostByType(s,MM);
            for(int i=0;i<costList.size();i++) {
                PieEntry pieEntry = new PieEntry((float) costList.get(i).getCost(), s);
                list.add(pieEntry);
            }


        }
        for(int i=0 ; i< list.size() ; i++)
        {
            Log.d(TAG,"list..."+list.get(i));
        }

        //?????????PieDataSet??????
        PieDataSet set = new PieDataSet(list , "") ;
        set.setDrawValues(false);//?????????true,???????????????y
        set.setAxisDependency(YAxis.AxisDependency.LEFT);//??????Y???,??????????????????????????????(?????????)????????????:???
        set.setAutomaticallyDisableSliceSpacing(false);//????????????,???????????????0???,?????????????????????????????????
        set.setSliceSpace(1f);//??????
        set.setSelectionShift(10f);//????????????????????????

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        set.setColors(colors);

        //??????PieData
        PieData data = new PieData(set);
        //?????????????????????????????????
        pieChart.setData(data);
        //????????????
        pieChart.invalidate();
        //?????????????????????????????????????????????
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }
}
