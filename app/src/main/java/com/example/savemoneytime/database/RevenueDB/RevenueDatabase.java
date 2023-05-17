package com.example.savemoneytime.database.RevenueDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.savemoneytime.MainApplication.Models.ActionUserRevenueModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyRevenueModel;
import com.example.savemoneytime.database.CategoryRevenueDAO;
import com.example.savemoneytime.database.SaveDatabase;
@Database(entities = {CatelogyRevenueModel.class, ActionUserRevenueModel.class}, version = 2, exportSchema = true)
public abstract class RevenueDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="revenue1.db";
    private static RevenueDatabase instance;
    public static synchronized RevenueDatabase getInstance(Context context){
        if(instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),RevenueDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CategoryRevenueDAO categoryRevenueDAO();
    public abstract ActionUserRevenueDao actionUserRevenueDao();
}
