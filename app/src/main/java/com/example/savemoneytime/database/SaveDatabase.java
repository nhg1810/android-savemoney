package com.example.savemoneytime.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyRevenueModel;
import com.example.savemoneytime.MainApplication.Models.Setting;

@Database(
        entities = {CatelogyModel.class, ActionUserModel.class, Setting.class}, version = 2, exportSchema = true)
public abstract class SaveDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="savemoney1.db";
    private static SaveDatabase instance;
    public static synchronized SaveDatabase getInstance(Context context){
        if(instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),SaveDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CategoryDAO categoryDAO();
    public abstract ActionUserDao actionUserDao();
    public abstract SettingUserDAO aSettingUserDAO();
//    public abstract CategoryRevenueDAO catelogyRevenueModel();
}
