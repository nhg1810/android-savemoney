package com.example.savemoneytime.MainApplication.FragmentInFragment.FirstFrag;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savemoneytime.Interface.IclickCategory;
import com.example.savemoneytime.MainApplication.Adapters.CatelogyAdapter;
import com.example.savemoneytime.MainApplication.Adapters.IconCategoryAdapter;
import com.example.savemoneytime.MainApplication.Data.IconCatelogy.DataIcon;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyModel;
import com.example.savemoneytime.R;
import com.example.savemoneytime.database.SaveDatabase;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.List;

public class ExpensesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private TextView viewCalender;
    private ImageButton nextDate,reload_data;
    private ImageButton previousDate;
    private RecyclerView rcvCatelogy;
    private List<CatelogyModel> listCatelogy;
    private CatelogyAdapter catelogyAdapter;
    private MaterialButton add_catelogy;
    private MaterialButton bt_cancel_dialog_add_catelogy;
    private MaterialButton bt_save_catelogy;
    private Spinner spinner_category;
    private IconCategoryAdapter iconCategoryAdapter;
    private EditText edt_title_category;
    private TextView alert_error,img_catelogy;
    private SwipeRefreshLayout mSrlLayout;
    private LinearLayout lo_contain_ct;
    private Calendar cld_chosse;
    private int idCateSelected=0;

    private int[]imageCategory ={R.drawable.icon_cate_bag,R.drawable.icon_cate_beauty,R.drawable.icon_cate_diet,
            R.drawable.icon_cate_hawaiian_shirt,R.drawable.icon_cate_house,R.drawable.icon_cate_nurses,
            R.drawable.icon_cate_studying,R.drawable.icon_cate_water
    };
    int i=0;
    View myView;
    Bitmap bmpImgCategory;

    public ExpensesFragment(){

    }
    private String onClickItem(CatelogyModel catelogyModel) {
        //xử lý sự kiện click item ở recycleview ở đây
        return catelogyModel.getTitleCatelogy();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_expenses, container, false);
        if(i==0){
            GetCurrentDate(i);
            cld_chosse = GetDateChosse(i);
        }
        //các thứ linh tinh để làm ngày giờ
        nextDate = myView.findViewById(R.id.bt_next_day);
        previousDate= myView.findViewById(R.id.bt_previous_day);
        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                GetCurrentDate(i);
                cld_chosse = GetDateChosse(i);
                if(i==0){
                    viewCalender.setBackgroundResource(R.color.background);
                }else{
                    viewCalender.setBackgroundResource(R.color.purple_200);
                }
            }
        });
        previousDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                GetCurrentDate(i);
                cld_chosse = GetDateChosse(i);
                if(i==0){
                    viewCalender.setBackgroundResource(R.color.background);
                }else{
                    viewCalender.setBackgroundResource(R.color.teal_700);
                }
            }
        });
        // reload data, kéo xuống để relaod

        mSrlLayout= myView.findViewById(R.id.swipe);
        mSrlLayout.setColorSchemeResources(R.color.colorTextPrimary, R.color.ColorActive, R.color.design_default_color_primary_dark);
        mSrlLayout.setOnRefreshListener(this);
        rcvCatelogy = myView.findViewById(R.id.flex_catelogy);

        clickShowThingItemCatelory();




//        add phần item category
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);

        //thêm đừng viền cho các item
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        rcvCatelogy.addItemDecoration(itemDecoration);
        rcvCatelogy.setLayoutManager(layoutManager);
        rcvCatelogy.setAdapter(catelogyAdapter);


        //nút thêm doanh mục
        add_catelogy=myView.findViewById(R.id.add_catelogy);
        add_catelogy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.BOTTOM);
            }
        });
        //sau khi chọn được doanh mục add nó vô csdl action người dùng
        ImageView save_action_user = (ImageView) myView.findViewById(R.id.save_action_user);
        save_action_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView Text_action = (TextView) myView.findViewById(R.id.Text_action);
                String ST_Text_action= Text_action.getText().toString();

                TextView value_text_payment = (TextView) myView.findViewById(R.id.value_text_payment);
                String ST_value_text_payment= value_text_payment.getText().toString();
                if(idCateSelected == 0 || ST_Text_action.equals("") || ST_value_text_payment.equals("")){
                    Toast.makeText(getContext(),"Chưa đủ thông tin !",Toast.LENGTH_LONG).show();
                }else{
                    //lưu vô csdl
                    ActionUserModel actionUserModel = new ActionUserModel(ST_Text_action, cld_chosse.getTime(),ST_value_text_payment,idCateSelected);
                    SaveDatabase.getInstance(getContext()).actionUserDao().insertActionUser(actionUserModel);
                    Toast.makeText(getContext(),"thêm thành công !",Toast.LENGTH_LONG).show();
                    //sau khi thêm xong xoá dữ liệu các textview;
                    Text_action.setText("");
                }

            }
        });

        return myView;
    }
    public void clickShowThingItemCatelory(){
        //vừa, gọi để add  adapter vừa xử lý mỗi khi click vào mọtt imgview  của category thì hiển thị ra thông tin của category đó
        catelogyAdapter = new CatelogyAdapter(addListCatelogyFromDatabase(listCatelogy), new IclickCategory() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickItemCategory(CatelogyModel catelogyModel) {
                TextView tv_show_cate_it_click = (TextView) myView.findViewById(R.id.tv_show_cate_it_click);
                tv_show_cate_it_click.setText(onClickItem(catelogyModel).toString());

                ImageView img_cate_selected = (ImageView) myView.findViewById(R.id.img_cate_selected);
                img_cate_selected.setImageResource(catelogyModel.getSourceImgCatelogy());
                idCateSelected = catelogyModel.getIdCategory();
            }
        });
    }
    //hàm xử lý các sự kiện trong dialog dialog
    public void openDialog(int gravity){
        final Dialog dialog= new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_catelogy);

        Window window = dialog.getWindow();
        if(window==null){
            return;
        }else{
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttributes= window.getAttributes();
            windowAttributes.gravity = gravity;
            window.setAttributes(windowAttributes);

            if(Gravity.BOTTOM == gravity){
                dialog.setCancelable(true);
            }else{
                dialog.setCancelable(false);
            }
//            //xử lý sự kiện nút Huỷ
            bt_cancel_dialog_add_catelogy = dialog.findViewById(R.id.bt_cancel_dialog_add_catelogy);
            bt_cancel_dialog_add_catelogy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            //dùng spinnner để add các item-view vào spinner, trong các item đó chứa cả ảnh và text
            //texxt view, vì vậy sẽ set id cho mỗi IconModel để sau này sử dụng dễ dàng
            spinner_category = dialog.findViewById(R.id.dropdown_list_cate);
            iconCategoryAdapter = new IconCategoryAdapter(getContext(), DataIcon.getIconlist());
            spinner_category.setAdapter(iconCategoryAdapter);
            //Xử lý sự kiện nút lưu doanh mục

            bt_save_catelogy = dialog.findViewById(R.id.save_catelogy);
            alert_error=dialog.findViewById(R.id.alert_error);
            edt_title_category=dialog.findViewById(R.id.edt_title_category);

            bt_save_catelogy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // lấy sử liệu từ các edit text để lưu vào database,
                    // lưu thành công hiển thị thông báo thành công, và tắt dialog
                    if(edt_title_category.getText().toString().equals("")){
                        alert_error.setText("Chưa nhập tên doanh mục");
                    }else{
                        String edt_value_title_category= edt_title_category.getText().toString().trim();
                        int indexValueImgCatelogy = spinner_category.getSelectedItemPosition();

                        alert_error.setText("");
                        //lưu vào cơ sở dữ liệu
                       int valueImgCate= imageCategory[indexValueImgCatelogy];
                       CatelogyModel catelogyModel = new CatelogyModel(valueImgCate, edt_value_title_category);
                        SaveDatabase.getInstance(getContext()).categoryDAO().insertCategory(catelogyModel);
                        dialog.dismiss();
                        Toast.makeText(
                                getContext(),
                                "Thêm doanh mục thành công, nhấn RELOAD để xuất hiện danh mục !",
                                Toast.LENGTH_LONG
                        ).show();
                        clickShowThingItemCatelory();
//
                    }

                }
            });

            //xử lý với spinner(drop list) khi thg này adapter 1 item view, thì giá trị nó khi lấy ra sẽ ntn

        }
        dialog.show();
    }

    //hàm này để add dử liệu cào List để chuẩn bị setDapter
    public List<CatelogyModel> addListCatelogyFromDatabase(List<CatelogyModel> LcatelogyModels){
        LcatelogyModels = SaveDatabase.getInstance(getContext()).categoryDAO().getListCategory();
        // móc dữ liệu từ databasse Sqlite vào đây
        //lấy dử liệu từ bảng category vào đây\
        return LcatelogyModels;
    }

    public Calendar GetDateChosse(int index){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,index);
        return calendar;
    }

    //hàm lấy ra ngày cần lấy
    public void GetCurrentDate(int index){
        viewCalender = myView.findViewById(R.id.show_calender);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,index);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK );
        int mounthOfWeek = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);
        String weekDay = "";
        String weekMounth="";
        if (Calendar.MONDAY == dayOfWeek) {
            weekDay = "Thứ hai";
        } else if (Calendar.TUESDAY == dayOfWeek) {
            weekDay = "Thứ ba";
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            weekDay = "Thứ tư";
        } else if (Calendar.THURSDAY == dayOfWeek) {
            weekDay = "Thứ năm";
        } else if (Calendar.FRIDAY == dayOfWeek) {
            weekDay = "Thứ sáu";
        } else if (Calendar.SATURDAY == dayOfWeek) {
            weekDay = "Thứ bảy";
        } else if (Calendar.SUNDAY == dayOfWeek) {
            weekDay = "Chủ nhật";
        }


        if (Calendar.JANUARY == mounthOfWeek) {
            weekMounth = "Tháng một";
        } else if (Calendar.FEBRUARY == mounthOfWeek) {
            weekMounth = "Tháng hai";
        } else if (Calendar.MARCH == mounthOfWeek) {
            weekMounth = "Tháng ba";
        } else if (Calendar.APRIL  == mounthOfWeek) {
            weekMounth = "Tháng tư";
        } else if (Calendar.MAY == mounthOfWeek) {
            weekMounth = "Tháng năm";
        } else if (Calendar.JUNE == mounthOfWeek) {
            weekMounth = "Tháng sáu";
        } else if (Calendar.JULY == mounthOfWeek) {
            weekMounth = "Tháng 7";
        } else if (Calendar.AUGUST == mounthOfWeek) {
            weekMounth = "Tháng tám";
        } else if (Calendar.SEPTEMBER == mounthOfWeek) {
            weekMounth = "Tháng chín";
        } else if (Calendar.OCTOBER == mounthOfWeek) {
            weekMounth = "Tháng mười";
        } else if (Calendar.NOVEMBER == mounthOfWeek) {
            weekMounth = "Tháng mười một";
        } else if (Calendar.DECEMBER == mounthOfWeek) {
            weekMounth = "Tháng mười hai";
        }
        viewCalender.setText(currentDay+"/ "+ weekMounth +"/ "+year+"( " +weekDay+" )");
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupAdapter();
                mSrlLayout.setRefreshing(false);
            }
        }, 2500);
    }
    //hàm này xử lý việc reload gọi lại adapter từ đầu để reload lại data
    private void setupAdapter() {
        rcvCatelogy = myView.findViewById(R.id.flex_catelogy);
        catelogyAdapter = new CatelogyAdapter(addListCatelogyFromDatabase(listCatelogy), new IclickCategory() {
            @Override
            public void onClickItemCategory(CatelogyModel catelogyModel) {
                TextView tv_show_cate_it_click = (TextView) myView.findViewById(R.id.tv_show_cate_it_click);
                tv_show_cate_it_click.setText(onClickItem(catelogyModel).toString());
                ImageView img_cate_selected = (ImageView) myView.findViewById(R.id.img_cate_selected);
                img_cate_selected.setImageResource(catelogyModel.getSourceImgCatelogy());
                idCateSelected = catelogyModel.getIdCategory();
            }
        });

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);

        rcvCatelogy.setLayoutManager(layoutManager);
        rcvCatelogy.setAdapter(catelogyAdapter);
    }
}