package com.example.savemoneytime.MainApplication.FragmentInFragment.SecondFrag;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.savemoneytime.Interface.IclickAction;
import com.example.savemoneytime.Interface.IclickRevenueAction;
import com.example.savemoneytime.MainApplication.Adapters.ListExpensesAdapter;
import com.example.savemoneytime.MainApplication.Adapters.ListRevenueAdapter;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.ActionUserRevenueModel;
import com.example.savemoneytime.R;
import com.example.savemoneytime.database.RevenueDB.RevenueDatabase;
import com.example.savemoneytime.database.SaveDatabase;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListRevenueFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View myview;
    RecyclerView rcl_contain_list_expenses;
    private ListRevenueAdapter listRevenueAdapter;
    private SwipeRefreshLayout reload_list_action;
    private List<ActionUserRevenueModel>LactionUserRevenueModels;
    private CustomCalendar ctcalendar;
    public int IdActionClick= 0 ;
    private List<ActionUserRevenueModel> Ldate_action;
    public ListRevenueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview= inflater.inflate(R.layout.fragment_list_revenue, container, false);
        reload_list_action= myview.findViewById(R.id.reload_list_action);
        reload_list_action.setColorSchemeResources(R.color.colorTextPrimary, R.color.ColorActive, R.color.design_default_color_primary_dark);
        reload_list_action.setOnRefreshListener(this);
        SetUpAdapter();

        //xử lý chiếc lịch
        ctcalendar = myview.findViewById(R.id.custom_calender);
        HashMap<Object, Property> desHashMap = new HashMap<>();
        Property defaultProperty = new Property();
        defaultProperty.layoutResource= R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.tv_calandar_show;
        desHashMap.put("default_view", defaultProperty);

        Property presentProperty = new Property();
        presentProperty.layoutResource= R.layout.present_date_view;
        presentProperty.dateTextViewResource = R.id.tv_calandar_show;
        desHashMap.put("normal", presentProperty);

        ctcalendar.setMapDescToProp(desHashMap);
        HashMap<Integer, Object> dateHashMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Ldate_action = RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().getListAction();
        for(int i =0;i<Ldate_action.size(); i++){
            calendar = dateToCalendar(Ldate_action.get(i).getDateTimeAction());
            dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH ),"normal");
            ctcalendar.setDate(calendar, dateHashMap);
        }
        return myview;
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
    public void SetUpAdapter(){
        listRevenueAdapter = new ListRevenueAdapter(addListActionFromDatabase(LactionUserRevenueModels), new IclickRevenueAction() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickItemAction(ActionUserRevenueModel actionUserModel) {
                IdActionClick = onClickItem(actionUserModel);
                //lấy id này rồi xoá chết mẹ nó
                RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().deleteById(IdActionClick);
                Toast.makeText(getContext(), "Xoá thành công !!, reload ", Toast.LENGTH_LONG).show();
                //reload lại các list
                SetUpAdapter();
            }
        });
        rcl_contain_list_expenses = myview.findViewById(R.id.rcl_contain_list_expenses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl_contain_list_expenses.setLayoutManager(layoutManager);
        rcl_contain_list_expenses.setAdapter(listRevenueAdapter);
    }
    public List<ActionUserRevenueModel> addListActionFromDatabase(List<ActionUserRevenueModel> LactionUserModels){
        LactionUserModels = RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().getListAction();
        //lấy dử liệu từ bảng category vào đây\
        return LactionUserModels;
    }
    private int onClickItem(ActionUserRevenueModel actionUserModel) {
        //xử lý sự kiện click item ở recycleview ở đây
        return actionUserModel.getIdAction();
    }
    //Convert Date to Calendar
    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }
}