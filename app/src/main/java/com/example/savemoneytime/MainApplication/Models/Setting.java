package com.example.savemoneytime.MainApplication.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "settinguser")
public class Setting implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int idSetting;
    private int weekless;
    private int normal;
    private int strong;

    public Setting(int weekless, int normal, int strong) {
        this.weekless = weekless;
        this.normal = normal;
        this.strong = strong;
    }

    public int getIdSetting() {
        return idSetting;
    }

    public void setIdSetting(int idSetting) {
        this.idSetting = idSetting;
    }

    public int getWeekless() {
        return weekless;
    }

    public void setWeekless(int weekless) {
        this.weekless = weekless;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getStrong() {
        return strong;
    }

    public void setStrong(int strong) {
        this.strong = strong;
    }
}
