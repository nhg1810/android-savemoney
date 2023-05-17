package com.example.savemoneytime.MainApplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savemoneytime.Interface.IclickAction;
import com.example.savemoneytime.Interface.IclickRevenueAction;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.ActionUserRevenueModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyRevenueModel;
import com.example.savemoneytime.R;
import com.example.savemoneytime.database.RevenueDB.RevenueDatabase;
import com.example.savemoneytime.database.SaveDatabase;

import java.util.List;

public class ListRevenueAdapter extends RecyclerView.Adapter<ListRevenueAdapter.ListExpensesViewHolder> {
    private List<ActionUserRevenueModel> l_actionuser;
    private IclickRevenueAction iclickAction;
    private Context context;

    public ListRevenueAdapter(List<ActionUserRevenueModel> l_actionuser, IclickRevenueAction iclickAction) {
        this.iclickAction=iclickAction;
        this.l_actionuser = l_actionuser;
    }

    @NonNull
    @Override
    public ListExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_list_revenue, parent,false);
        return new ListRevenueAdapter.ListExpensesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListExpensesViewHolder holder, int position) {
        ActionUserRevenueModel actionUserModel = l_actionuser.get(position);
        if(actionUserModel==null){
            return;
        }
         List<CatelogyRevenueModel>l_category;
        //gọi lại từ thằng dababase để lấy ảnh ra
        l_category = RevenueDatabase.getInstance(context).categoryRevenueDAO().getListByIdCategoryRevenue(actionUserModel.getIdCategory());

        CatelogyRevenueModel catelogyModel = l_category.get(0);
        holder.imgAction.setImageResource(catelogyModel.getSourceImgCatelogy());

        holder.titleExpenses.setText(actionUserModel.getTitleAction());
        holder.ExpensesText.setText(actionUserModel.getPaymentAction());
        holder.dateText.setText(String.valueOf(actionUserModel.getDateTimeAction()));
        holder.del_action_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iclickAction.onClickItemAction(actionUserModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return l_actionuser.size();
    }

    public class ListExpensesViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgAction;
        private TextView titleExpenses;
        private TextView ExpensesText;
        private TextView dateText;
        private ImageButton del_action_user;
        public ListExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAction = itemView.findViewById(R.id.img_expenses);
            titleExpenses = itemView.findViewById(R.id.title_action);
            ExpensesText = itemView.findViewById(R.id.expenses_text);
            dateText = itemView.findViewById(R.id.date);
            del_action_user = itemView.findViewById(R.id.del_action_user);

        }
    }
}
