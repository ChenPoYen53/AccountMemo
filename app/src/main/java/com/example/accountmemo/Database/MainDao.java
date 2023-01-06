package com.example.accountmemo.Database;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao
{
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    @Delete
    void delete(MainData mainData);

    @Delete
    void deleteAll(List<MainData> mainDataList);


    @Query("SELECT * FROM keeper WHERE inout LIKE :income")
    List<MainData> getIncome(String income);

    @Query("SELECT * FROM keeper WHERE inout LIKE :expense")
    List<MainData> getExpense(String expense);

    @Query("SELECT * FROM keeper WHERE inout LIKE :income AND asset LIKE :asset")
    List<MainData> getIncomeByType(String income,String asset);

    @Query("SELECT * FROM keeper WHERE inout LIKE :expense AND asset LIKE :asset")
    List<MainData> getExpenseByType(String expense,String asset);

    @Query("SELECT * FROM keeper WHERE year LIKE :year AND month LIKE :month")
    List<MainData> getDataByYM(int year , int month);

    @Query("SELECT * FROM keeper WHERE inout LIKE :income AND year LIKE :year AND month LIKE :month AND date LIKE :date")
    List<MainData> getDailyIncome(String income , int year , int month , int date);

    @Query("SELECT * FROM keeper WHERE inout LIKE :expense AND year LIKE :year AND month LIKE :month AND date LIKE :date")
    List<MainData> getDailyExpense(String expense,int year , int month , int date);

    @Query("SELECT * FROM keeper WHERE inout LIKE :income AND year LIKE :year AND month LIKE :month")
    List<MainData> getMonthIncome(String income,int year,int month);

    @Query("SELECT * FROM keeper WHERE inout LIKE :expense AND year LIKE :year AND month LIKE :month")
    List<MainData> getMonthExpense(String expense,int year,int month);

    @Query("SELECT * FROM keeper WHERE type LIKE :type AND month LIKE :month")
    List<MainData> getCostByType(String type,int month);

    @Query("SELECT * FROM keeper")
    List<MainData> getAll();

    @Query("UPDATE keeper SET inout = :inout WHERE ID = :sID")
    void updateInout(String inout , int sID);


    @Query("UPDATE keeper SET asset = :asset WHERE ID = :sID")
    void updateAsset(String asset , int sID);

    @Query("UPDATE keeper SET cost = :cost WHERE ID = :sID")
    void updateCost(String cost , int sID);

    @Query("UPDATE keeper SET date = :date WHERE ID = :sID")
    void updateDate(int date , int sID);

    @Query("UPDATE keeper SET month = :month WHERE ID = :sID")
    void updateMonth(int month , int sID);

    @Query("UPDATE keeper SET year = :year WHERE ID = :sID")
    void updateYear(int year , int sID);

    @Query("UPDATE keeper SET total = :total WHERE ID = :sID")
    void updateTotal(String total , int sID);

    @Query("UPDATE keeper SET date2 = :date2 WHERE ID = :sID")
    void updateDate2(String date2 , int sID);

    @Query("UPDATE keeper SET cost = :type WHERE ID = :sID")
    void updateType(String type , int sID);
}
