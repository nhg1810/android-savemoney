package com.example.savemoneytime.MainApplication.FragmentInFragment.FirstFrag;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savemoneytime.Interface.IclickCategory;
import com.example.savemoneytime.Interface.IclickCategoryRevenue;
import com.example.savemoneytime.MainApplication.Adapters.CatelogyAdapter;
import com.example.savemoneytime.MainApplication.Adapters.CatelogyRevenueAdapter;
import com.example.savemoneytime.MainApplication.Adapters.IconCategoryAdapter;
import com.example.savemoneytime.MainApplication.Adapters.IconCategoryRevenueAdapter;
import com.example.savemoneytime.MainApplication.Data.IconCatelogy.DataIcon;
import com.example.savemoneytime.MainApplication.Data.IconRevenueCategory.DataRevenueIcon;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.ActionUserRevenueModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyRevenueModel;
import com.example.savemoneytime.R;
import com.example.savemoneytime.database.RevenueDB.RevenueDatabase;
import com.example.savemoneytime.database.SaveDatabase;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.List;

public class RevenueFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TextView viewCalender,alert_error;
    private ImageButton nextDate,previousDate;
    private MaterialButton add_catelogy,bt_cancel_dialog_add_catelogy,bt_save_catelogy;
    private Spinner spinner_category;
    private IconCategoryRevenueAdapter iconCategoryRevenueAdapter;
    private EditText edt_title_category;
    private CatelogyRevenueAdapter catelogyAdapter;
    private List<CatelogyRevenueModel> listCatelogy;
    int idCateSelected=0;
    private RecyclerView rcvCatelogy;
    private SwipeRefreshLayout mSrlLayout;
    private Calendar cld_chosse;

    int i=0;
    View myView1;

    private int[]imageCategory ={R.drawable.handsome,R.drawable.team,R.drawable.dollar,
            R.drawable.coding,R.drawable.lotus,R.drawable.father,
            R.drawable.salary,R.drawable.mother
    };
    public RevenueFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView1 =inflater.inflate(R.layout.fragment_revenue, container, false);
        if(i==0){
            GetCurrentDate(i);
            cld_chosse = GetDateChosse(i);
        }
//        //các thứ linh tinh để làm ngày giờ
        nextDate = myView1.findViewById(R.id.bt_next_day);
        previousDate= myView1.findViewById(R.id.bt_previous_day);

        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                cld_chosse = GetDateChosse(i);
                GetCurrentDate(i);
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
                cld_chosse = GetDateChosse(i);
                GetCurrentDate(i);
                if(i==0){
                    viewCalender.setBackgroundResource(R.color.background);
                }else{
                    viewCalender.setBackgroundResource(R.color.teal_700);
                }
            }
        });

        mSrlLayout= myView1.findViewById(R.id.swipe);
        mSrlLayout.setColorSchemeResources(R.color.colorTextPrimary, R.color.ColorActive, R.color.design_default_color_primary_dark);
        mSrlLayout.setOnRefreshListener(this);
        rcvCatelogy = myView1.findViewById(R.id.flex_catelogy);

        //nút bật dialog
        add_catelogy=myView1.findViewById(R.id.add_catelogy);
        add_catelogy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.BOTTOM);
            }
        });

        clickShowThingItemCatelory();
//        add phần item category
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);

//        thêm đừng viền cho các item
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        rcvCatelogy.addItemDecoration(itemDecoration);
        rcvCatelogy.setLayoutManager(layoutManager);
        rcvCatelogy.setAdapter(catelogyAdapter);

        //sau khi chọn được doanh mục add nó vô csdl action người dùng
        ImageView save_action_user = (ImageView) myView1.findViewById(R.id.save_action_user);
        save_action_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView Text_action = (TextView) myView1.findViewById(R.id.Text_action);
                String ST_Text_action= Text_action.getText().toString();

                TextView value_text_payment = (TextView) myView1.findViewById(R.id.value_text_payment);
                String ST_value_text_payment= value_text_payment.getText().toString();
                if(idCateSelected == 0 || ST_Text_action.equals("") || ST_value_text_payment.equals("")){
                    Toast.makeText(getContext(),"Chưa đủ thông tin !",Toast.LENGTH_LONG).show();
                }else{

                    //lưu vô csdl
                    ActionUserRevenueModel actionUserModel = new ActionUserRevenueModel(ST_Text_action,cld_chosse.getTime(),ST_value_text_payment,idCateSelected);
                    RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().insertActionUser(actionUserModel);
                    Toast.makeText(getContext(),"thêm thành công !",Toast.LENGTH_LONG).show();

                    //sau khi thêm xong xoá dữ liệu các textview;
                    Text_action.setText("");
                }

            }
        });

        return myView1;
    }
//    hàm tính ngày giờ
    public void GetCurrentDate(int index){
        viewCalender = myView1.findViewById(R.id.show_calender);
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

    public void clickShowThingItemCatelory(){
        //vừa, gọi để add  adapter vừa xử lý mỗi khi click vào mọtt imgview  của category thì hiển thị ra thông tin của category đó
        catelogyAdapter = new CatelogyRevenueAdapter(addListCatelogyFromDatabase(listCatelogy), new IclickCategoryRevenue() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickItemCategory(CatelogyRevenueModel catelogyRevenueModel) {
                TextView tv_show_cate_it_click = (TextView) myView1.findViewById(R.id.tv_show_cate_it_click);
                tv_show_cate_it_click.setText(onClickItem(catelogyRevenueModel).toString());

                ImageView img_cate_selected = (ImageView) myView1.findViewById(R.id.img_cate_selected);
                img_cate_selected.setImageResource(catelogyRevenueModel.getSourceImgCatelogy());
                idCateSelected = catelogyRevenueModel.getIdCategory();
            }

            private String onClickItem(CatelogyRevenueModel catelogyRevenueModel) {
                return catelogyRevenueModel.getTitleCatelogy();
            }


        });
    }
    //hàm bật dialog
    public void openDialog(int gravity){
        final Dialog dialog= new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_category_revenue);

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
            iconCategoryRevenueAdapter = new IconCategoryRevenueAdapter(getContext(), DataRevenueIcon.getIconlist());
            spinner_category.setAdapter(iconCategoryRevenueAdapter);
            //Xử lý sự kiện nút lưu doanh mục

            bt_save_catelogy = dialog.findViewById(R.id.save_catelogy);
            alert_error=dialog.findViewById(R.id.alert_error);
            edt_title_category=dialog.findViewById(R.id.edt_title_category);

            //nút save category
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
                        CatelogyRevenueModel catelogyModel = new CatelogyRevenueModel(valueImgCate, edt_value_title_category);
                        RevenueDatabase.getInstance(getContext()).categoryRevenueDAO().insertCategoryRevenue(catelogyModel);
                        dialog.dismiss();
                        Toast.makeText(
                                getContext(),
                                "Thêm doanh mục thành công, nhấn RELOAD để xuất hiện danh mục !",
                                Toast.LENGTH_LONG
                        ).show();
//                        clickShowThingItemCatelory();
                    }

                }
            });

            //xử lý với spinner(drop list) khi thg này adapter 1 item view, thì giá trị nó khi lấy ra sẽ ntn

        }
        dialog.show();
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
        rcvCatelogy = myView1.findViewById(R.id.flex_catelogy);
        catelogyAdapter = new CatelogyRevenueAdapter(addListCatelogyFromDatabase(listCatelogy), new IclickCategoryRevenue() {
            @Override
            public void onClickItemCategory(CatelogyRevenueModel catelogyRevenueModel) {
                TextView tv_show_cate_it_click = (TextView) myView1.findViewById(R.id.tv_show_cate_it_click);
                tv_show_cate_it_click.setText(onClickItem(catelogyRevenueModel).toString());
                ImageView img_cate_selected = (ImageView) myView1.findViewById(R.id.img_cate_selected);
                img_cate_selected.setImageResource(catelogyRevenueModel.getSourceImgCatelogy());
                idCateSelected = catelogyRevenueModel.getIdCategory();
            }
        });

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);

        rcvCatelogy.setLayoutManager(layoutManager);
        rcvCatelogy.setAdapter(catelogyAdapter);

    }
    public List<CatelogyRevenueModel> addListCatelogyFromDatabase(List<CatelogyRevenueModel> LcatelogyModels){
        LcatelogyModels = RevenueDatabase.getInstance(getContext()).categoryRevenueDAO().getListCategoryRevenue();
        // móc dữ liệu từ databasse Sqlite vào đây
        //lấy dử liệu từ bảng category vào đây\
        return LcatelogyModels;
    }

    private String onClickItem(CatelogyRevenueModel catelogyModel) {
        //xử lý sự kiện click item ở recycleview ở đây
        return catelogyModel.getTitleCatelogy();
    }
    public Calendar GetDateChosse(int index){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,index);
        return calendar;
    }
}