package com.example.accountmemo;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class incomeFragment extends Fragment
{
    private static final String TAG = "incomeFragment";
    private static final String INCOME = "Income";
    private PieChart pieChart;
    private RoomDB roomDB;
    private List<MainData> mainDataList = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();
    private Set<String> set = new HashSet<>();
    private Set<String> exist = new HashSet<>();
    private List<PieEntry> list = new ArrayList<>() ;
    private List<MainData> costList = new ArrayList<>();
    private TextView txv;
    private String Format = "yyyy/MM";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_income_fragment,container,false);

        roomDB = RoomDB.getInstance(view.getContext());
        mainDataList = roomDB.mainDao().getAll();

        pieChart = view.findViewById(R.id.income_piechart);
        txv = view.findViewById(R.id.income_txv);

        txv.setText(calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH+1));

        show(view);

        return view;
    }
    private void show(View view)
    {
        pieChart.setUsePercentValues(true);
        //如果這個元件應該啟用(應該被繪製)FALSE如果沒有。如果禁用,此元件的任何內容將被繪製預設
        pieChart.getDescription().setEnabled(false);
        //將額外的偏移量(在圖表檢視周圍)附加到自動計算的偏移量
        pieChart.setExtraOffsets(5, 10, 5, 5);
        //較高的值表明速度會緩慢下降 例如如果它設定為0,它會立即停止。1是一個無效的值,並將自動轉換為0.999f。
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //設定中間字型
        String monthly = view.getResources().getString(R.string.monthly).toUpperCase(Locale.ROOT);
        String income = view.getResources().getString(R.string.income).toUpperCase(Locale.ROOT);
        pieChart.setCenterText(monthly+"\n"+income);
        pieChart.setEntryLabelColor(Color.BLACK);
        //設定為true將餅中心清空
        pieChart.setDrawHoleEnabled(true);
        //套孔,繪製在PieChart中心的顏色
        pieChart.setHoleColor(Color.WHITE);
        //設定透明圓應有的顏色。
        pieChart.setTransparentCircleColor(Color.WHITE);
        //設定透明度圓的透明度應該有0 =完全透明,255 =完全不透明,預設值為100。
        pieChart.setTransparentCircleAlpha(110);
        //設定在最大半徑的百分比餅圖中心孔半徑(最大=整個圖的半徑),預設為50%
        pieChart.setHoleRadius(30f);
        //設定繪製在孔旁邊的透明圓的半徑,在最大半徑的百分比在餅圖*(max =整個圖的半徑),預設55% -> 5%大於中心孔預設
        pieChart.setTransparentCircleRadius(35f);
        //將此設定為true,以繪製顯示在pie chart
        pieChart.setDrawCenterText(true);
        //集度的radarchart旋轉偏移。預設270f -->頂(北)
        pieChart.setRotationAngle(0);
        //設定為true,使旋轉/旋轉的圖表觸控。設定為false禁用它。預設值:true
        pieChart.setRotationEnabled(true);
        //將此設定為false,以防止由抽頭姿態突出值。值仍然可以通過拖動或程式設計高亮顯示。預設值:真
        pieChart.setHighlightPerTapEnabled(true);
        //建立Legend物件
        Legend l = pieChart.getLegend();
        l.setEnabled(false);

        //設定入口標籤的顏色。
        pieChart.setEntryLabelColor(Color.WHITE);
        //設定入口標籤的大小。預設值:13dp
        pieChart.setEntryLabelTextSize(12f);

        roomDB = RoomDB.getInstance(view.getContext());

        mainDataList = roomDB.mainDao().getMonthIncome(INCOME,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH+1));

        Log.d(TAG,"TODAY..."+calendar.get(Calendar.MONTH+1));

        ArrayList<String> typeList = new ArrayList();
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
            costList = roomDB.mainDao().getCostByType(s,calendar.get(Calendar.MONTH+1));
            for(int i=0;i<costList.size();i++) {
                PieEntry pieEntry = new PieEntry((float) costList.get(i).getCost(), s);
                list.add(pieEntry);
            }


        }
        for(int i=0 ; i< list.size() ; i++)
        {
            Log.d(TAG,"list..."+list.get(i));
        }

        //設定到PieDataSet物件
        PieDataSet set = new PieDataSet(list , "表一") ;
        set.setDrawValues(false);//設定為true,在圖表繪製y
        set.setAxisDependency(YAxis.AxisDependency.LEFT);//設定Y軸,這個資料集應該被繪製(左或右)。預設值:左
        set.setAutomaticallyDisableSliceSpacing(false);//當啟用時,片間距將是0時,最小值要小於片間距本身
        set.setSliceSpace(1f);//間隔
        set.setSelectionShift(10f);//點選伸出去的距離
        /**
         * 設定該資料集前應使用的顏色。顏色使用只要資料集所代表的條目數目高於顏色陣列的大小。
         * 如果您使用的顏色從資源, 確保顏色已準備好(通過呼叫getresources()。getColor(…))之前,將它們新增到資料集
         * */
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
        //傳入PieData
        PieData data = new PieData(set);
        //為圖表設定新的資料物件
        pieChart.setData(data);
        //重新整理
        pieChart.invalidate();
        //動畫圖上指定的動畫時間軸的繪製
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }
}
