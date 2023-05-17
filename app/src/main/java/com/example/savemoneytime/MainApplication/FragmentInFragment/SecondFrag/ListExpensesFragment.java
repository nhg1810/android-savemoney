package com.example.savemoneytime.MainApplication.FragmentInFragment.SecondFrag;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savemoneytime.Interface.IclickAction;
import com.example.savemoneytime.MainApplication.Adapters.ListExpensesAdapter;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyModel;
import com.example.savemoneytime.MainApplication.Models.Setting;
import com.example.savemoneytime.R;
import com.example.savemoneytime.database.SaveDatabase;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.button.MaterialButton;

import org.joda.time.DateTime;
import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListExpensesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View myview;
    RecyclerView rcl_contain_list_expenses;
    private ListExpensesAdapter listExpensesAdapter;
    private List<ActionUserModel>LactionUserModels;
    private List<Setting> L_setting;
    private CustomCalendar ctcalendar;
    private TextView tv_lv1,tv_lv2,tv_lv3;
    private ImageButton bt_setting_user;
    private EditText edt_weekless,edt_normal,edt_strong;
    private MaterialButton save_setting_user;
    private List<ActionUserModel> Ldate_action;
    private List<Calendar> L_date_user_action;
    public int IdActionClick= 0 ;
    private SwipeRefreshLayout reload_list_action;
    public ListExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_list_expenses, container, false);
        //truyền dữ liệu cho adapter
        SetUpAdapter();

        //thêm mặc định setting
        Setting df_setting = new Setting(30000,50000,10000);
        SaveDatabase.getInstance(getContext()).aSettingUserDAO().insertSetting(df_setting);

        // reload data, kéo xuống để relaod
        reload_list_action= myview.findViewById(R.id.reload_list_action);
        reload_list_action.setColorSchemeResources(R.color.colorTextPrimary, R.color.ColorActive, R.color.design_default_color_primary_dark);
        reload_list_action.setOnRefreshListener(this);


        //xử lý chiếc lịch ở đây nè
        ctcalendar = myview.findViewById(R.id.custom_calender);

        HashMap<Object, Property > desHashMap = new HashMap<>();

        Property defaultProperty = new Property();
        defaultProperty.layoutResource= R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.tv_calandar_show;
        desHashMap.put("default_view", defaultProperty);

        Property currentProperty = new Property();
        currentProperty.layoutResource= R.layout.current_view_calendar;
        currentProperty.dateTextViewResource = R.id.tv_calandar_show;
        desHashMap.put("weekless",currentProperty);

        Property presentProperty = new Property();
        presentProperty.layoutResource= R.layout.present_date_view;
        presentProperty.dateTextViewResource = R.id.tv_calandar_show;
        desHashMap.put("normal", presentProperty);

        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_calendar_view;
        absentProperty.dateTextViewResource= R.id.tv_calandar_show;
        desHashMap.put("strong",absentProperty);


        //xử lý ở đây
        ctcalendar.setMapDescToProp(desHashMap);
        HashMap<Integer, Object> dateHashMap = new HashMap<>();

        //lấy các ngày từ csdl để xử lý màu mè hoa lá hẹ
        Ldate_action = SaveDatabase.getInstance(getContext()).actionUserDao().getListAction();
        Calendar calendar = Calendar.getInstance();

        if(Ldate_action.size()!=0){
            Calendar FirtDateInlist = dateToCalendar(Ldate_action.get(0).getDateTimeAction());
            int Sum=0;

            for(int i = 0; i < Ldate_action.size(); i++) {
                calendar = dateToCalendar(Ldate_action.get(i).getDateTimeAction());
                if(FirtDateInlist == calendar){
                    Sum+=Integer.parseInt(Ldate_action.get(i).getPaymentAction());
                }else{
                    L_setting = SaveDatabase.getInstance(getContext()).aSettingUserDAO().getsetting();
                    if(Sum <= L_setting.get(0).getWeekless() ){
                        dateHashMap.put(FirtDateInlist.get(Calendar.DAY_OF_MONTH ),"weekless");
                    }else if(Sum >= L_setting.get(0).getWeekless() && Sum <= L_setting.get(0).getNormal() ){
                        dateHashMap.put(FirtDateInlist.get(Calendar.DAY_OF_MONTH ),"normal");
                    }else{
                        dateHashMap.put(FirtDateInlist.get(Calendar.DAY_OF_MONTH ),"strong");
                    }

                    FirtDateInlist = calendar;
                    Sum = Integer.parseInt(Ldate_action.get(i).getPaymentAction());
                }
                L_setting = SaveDatabase.getInstance(getContext()).aSettingUserDAO().getsetting();
                if(Sum <= L_setting.get(0).getWeekless() ){
                    dateHashMap.put(FirtDateInlist.get(Calendar.DAY_OF_MONTH ),"weekless");
                }else if(Sum >= L_setting.get(0).getWeekless() && Sum <= L_setting.get(0).getNormal() ){
                    dateHashMap.put(FirtDateInlist.get(Calendar.DAY_OF_MONTH ),"normal");
                }else{
                    dateHashMap.put(FirtDateInlist.get(Calendar.DAY_OF_MONTH ),"strong");
                }
            }
            ctcalendar.setDate(calendar, dateHashMap);
        }else{
            dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH ),"default_view");
        }
        ctcalendar.setDate(calendar, dateHashMap);
//        //thiết lập các mức tình trạng tiền ở đây

        bt_setting_user= myview.findViewById(R.id.bt_setting_user);
        bt_setting_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog(Gravity.BOTTOM);
            }
        });

        ctcalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                //
            }
        });


        return myview;
    }
    //hàm này để add dử liệu cào List để chuẩn bị setDapter
    public List<ActionUserModel> addListActionFromDatabase(List<ActionUserModel> LactionUserModels){
        LactionUserModels = SaveDatabase.getInstance(getContext()).actionUserDao().getListAction();
        //lấy dử liệu từ bảng category vào đây\
        return LactionUserModels;
    }
    //hàm xử lý các báo động về mức tiêu tiền
    public void OpenDialog(int gravity){
        final Dialog dialog= new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting_user);

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
            //XỬ LÝ LƯU CÁC SETTING Ở ĐÂY
            edt_weekless = dialog.findViewById(R.id.edt_weekless);
            edt_normal = dialog.findViewById(R.id.edt_normal);
            edt_strong = dialog.findViewById(R.id.edt_strong);

            tv_lv1= dialog.findViewById(R.id.tv_lv1);
            tv_lv2= dialog.findViewById(R.id.tv_lv2);
            tv_lv3= dialog.findViewById(R.id.tv_lv3);

            save_setting_user= dialog.findViewById(R.id.save_setting_user);

            //lấy từ csdl setting user ra đổ vào nếu csdl rỗng thì hiển thị placholder
            L_setting = SaveDatabase.getInstance(getContext()).aSettingUserDAO().getsetting();
            if(L_setting.size() == 0){
                edt_weekless.setHint("Thiết lập mức tiêu thấp nhất");
                edt_normal.setHint("Thiết lập tên mức tiêu bình thường");
                edt_strong.setHint("Thiết lập tên mức tiêu báo động");
            }else{

                Setting setting = L_setting.get(0);
                tv_lv1.setText("mức1: "+String.valueOf(setting.getWeekless()));
                tv_lv2.setText("mức2: "+String.valueOf(setting.getNormal()));
                tv_lv3.setText("mức3: "+String.valueOf(setting.getStrong()));

                edt_weekless.setText(String.valueOf(setting.getWeekless()));
                edt_normal.setText(String.valueOf(setting.getNormal()));
                edt_strong.setText(String.valueOf(setting.getStrong()));
            }



            //lưu vô csdl setting user
            //khi bấm nút lưu thì lưu các dữ liệu từ edittexts
            save_setting_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String stm1 = edt_weekless.getText().toString();
                    String stm2 = edt_normal.getText().toString();
                    String stm3 = edt_strong.getText().toString();

                    int m1 = Integer.parseInt(stm1);
                    int m2 = Integer.parseInt(stm2);
                    int m3 = Integer.parseInt(stm3);
                        if(L_setting.size() == 0 && stm1 != "" && stm2 != "" && stm3 != "" ){
                            Setting setting = new Setting(m1,m2,m3);
                            SaveDatabase.getInstance(getContext()).aSettingUserDAO().insertSetting(setting);
                            dialog.dismiss();
                            Toast.makeText(getContext(),"Thêm thiết lập thành công !", Toast.LENGTH_LONG)
                                    .show();
                        }else{
                            SaveDatabase.getInstance(getContext()).aSettingUserDAO().update(m1,m2,m3);
                            dialog.dismiss();
                            Toast.makeText(getContext(),"Cập nhật thành công !", Toast.LENGTH_LONG)
                                    .show();
                    }
                }
            });
            //sự kiện nút huỷ
            MaterialButton bt_cancel_dialon =(MaterialButton) dialog.findViewById(R.id.bt_cancel_dialog);
            bt_cancel_dialon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });




        }
        dialog.show();
    }

    //click vô  setting nào thì return lại id khi bấm lúc ấy
    private int onClickItem(ActionUserModel actionUserModel) {
        //xử lý sự kiện click item ở recycleview ở đây
        return actionUserModel.getIdAction();
    }
    public void SetUpAdapter(){
        listExpensesAdapter = new ListExpensesAdapter(addListActionFromDatabase(LactionUserModels), new IclickAction() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickItemAction(ActionUserModel actionUserModel) {
                IdActionClick = onClickItem(actionUserModel);
                //lấy id này rồi xoá chết mẹ nó
                SaveDatabase.getInstance(getContext()).actionUserDao().deleteById(IdActionClick);
                Toast.makeText(getContext(), "Xoá thành công !!, reload ", Toast.LENGTH_LONG).show();
                //reload lại các list
                SetUpAdapter();
            }
        });
        rcl_contain_list_expenses = myview.findViewById(R.id.rcl_contain_list_expenses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl_contain_list_expenses.setLayoutManager(layoutManager);
        rcl_contain_list_expenses.setAdapter(listExpensesAdapter);


    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SetUpAdapter();
                reload_list_action.setRefreshing(false);
            }
        }, 2500);
    }
    //Convert Date to Calendar
    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    //Convert Calendar to Date
    private Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}