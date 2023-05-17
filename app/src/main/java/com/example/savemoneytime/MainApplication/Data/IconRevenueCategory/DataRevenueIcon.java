package com.example.savemoneytime.MainApplication.Data.IconRevenueCategory;

import com.example.savemoneytime.R;

import java.util.ArrayList;
import java.util.List;

public class DataRevenueIcon {
    public static List<IconRevenue> getIconlist(){
        List<IconRevenue> iconlist = new ArrayList<>();

        IconRevenue iconRevenueShopping = new IconRevenue("Đẹp trai", R.drawable.handsome,0);
        iconlist.add(iconRevenueShopping);


        IconRevenue iconRevenueGirl = new IconRevenue("Công nhân", R.drawable.team,1);
        iconlist.add(iconRevenueGirl);

        IconRevenue iconRevenueFood = new IconRevenue("Tiền ảo", R.drawable.dollar,2);
        iconlist.add(iconRevenueFood);

        IconRevenue iconRevenueShirt = new IconRevenue("Coder", R.drawable.coding,3);
        iconlist.add(iconRevenueShirt);

        IconRevenue iconRevenueHouse = new IconRevenue("Lô tô", R.drawable.lotus,4);
        iconlist.add(iconRevenueHouse);

        IconRevenue iconRevenueNurse = new IconRevenue("Ba cho", R.drawable.father,5);
        iconlist.add(iconRevenueNurse);

        IconRevenue iconRevenueStudy = new IconRevenue("Tiền lương", R.drawable.salary,6);
        iconlist.add(iconRevenueStudy);

        IconRevenue iconRevenueWater = new IconRevenue("Tiền mẹ cho", R.drawable.mother,7);
        iconlist.add(iconRevenueWater);
        return  iconlist;
    }
}
