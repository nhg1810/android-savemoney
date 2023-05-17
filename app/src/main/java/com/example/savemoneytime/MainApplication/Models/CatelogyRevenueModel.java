package com.example.savemoneytime.MainApplication.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "categoryrevenue")
public class CatelogyRevenueModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int idCategory;
    private int sourceImgCatelogy;
    private String titleCatelogy;

    public CatelogyRevenueModel(int sourceImgCatelogy, String titleCatelogy) {
        this.sourceImgCatelogy = sourceImgCatelogy;
        this.titleCatelogy = titleCatelogy;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getSourceImgCatelogy() {
        return sourceImgCatelogy;
    }

    public void setSourceImgCatelogy(int sourceImgCatelogy) {
        this.sourceImgCatelogy = sourceImgCatelogy;
    }

    public String getTitleCatelogy() {
        return titleCatelogy;
    }

    public void setTitleCatelogy(String titleCatelogy) {
        this.titleCatelogy = titleCatelogy;
    }
}
