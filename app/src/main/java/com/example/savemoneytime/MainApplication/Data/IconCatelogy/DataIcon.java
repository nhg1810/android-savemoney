package com.example.savemoneytime.MainApplication.Data.IconCatelogy;

import com.example.savemoneytime.R;

import java.util.ArrayList;
import java.util.List;

public class DataIcon {
    public static List<Icon> getIconlist(){
        List<Icon> iconlist = new ArrayList<>();

        Icon iconShopping = new Icon("Shopping", R.drawable.icon_cate_bag,0);
        iconlist.add(iconShopping);


        Icon iconGirl = new Icon("Người yêu", R.drawable.icon_cate_beauty,1);
        iconlist.add(iconGirl);

        Icon iconFood = new Icon("Đi ăn", R.drawable.icon_cate_diet,2);
        iconlist.add(iconFood);

        Icon iconShirt = new Icon("Áo quần", R.drawable.icon_cate_hawaiian_shirt,3);
        iconlist.add(iconShirt);

        Icon iconHouse = new Icon("Tiền nhà", R.drawable.icon_cate_house,4);
        iconlist.add(iconHouse);

        Icon iconNurse = new Icon("Đi khám", R.drawable.icon_cate_nurses,5);
        iconlist.add(iconNurse);

        Icon iconStudy = new Icon("Học thêm", R.drawable.icon_cate_studying,6);
        iconlist.add(iconStudy);

        Icon iconWater = new Icon("Tiền nước", R.drawable.icon_cate_water,7);
        iconlist.add(iconWater);
        return  iconlist;
    }
}
