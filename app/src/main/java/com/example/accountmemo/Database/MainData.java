package com.example.accountmemo.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "keeper")
public class MainData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "inout")
    private String inout;
    @ColumnInfo(name = "asset")
    private String asset;
    @ColumnInfo(name = "cost")
    private double cost;
    @ColumnInfo(name = "day")
    private String day;
    @ColumnInfo(name = "date")
    private int date;
    @ColumnInfo(name = "month")
    private int month;
    @ColumnInfo(name = "year")
    private int year;
    @ColumnInfo(name = "total")
    private String total;
    @ColumnInfo(name = "date2")
    private String date2;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "incomeCost")
    private long incomeCost;
    @ColumnInfo(name = "expenseCost")
    private long expenseCost;

    public long getIncomeCost() {
        return incomeCost;
    }

    public void setIncomeCost(long incomeCost) {
        this.incomeCost = incomeCost;
    }

    public long getExpenseCost() {
        return expenseCost;
    }

    public void setExpenseCost(long expenseCost) {
        this.expenseCost = expenseCost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInout() {
        return inout;
    }

    public void setInout(String inout) {
        this.inout = inout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
