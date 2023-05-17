package com.example.savemoneytime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.SkipQueryVerification;

import com.example.savemoneytime.MainApplication.Models.ActionUserModel;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Dao
public interface ActionUserDao {
    @Query("DELETE FROM actionuser WHERE idAction = :idAction")
    void deleteById(int idAction);

    @Insert
    void insertActionUser(ActionUserModel actionUserModel);

    @Query("SELECT * FROM actionuser")
    List<ActionUserModel> getListAction();

}
