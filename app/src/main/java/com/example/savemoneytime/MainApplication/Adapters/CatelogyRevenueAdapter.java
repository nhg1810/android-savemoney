package com.example.savemoneytime.MainApplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savemoneytime.Interface.IclickCategory;
import com.example.savemoneytime.Interface.IclickCategoryRevenue;
import com.example.savemoneytime.MainApplication.Models.CatelogyModel;
import com.example.savemoneytime.MainApplication.Models.CatelogyRevenueModel;
import com.example.savemoneytime.R;

import java.util.List;

public class CatelogyRevenueAdapter extends  RecyclerView.Adapter<CatelogyRevenueAdapter.CatelogyViewHolder>{
    private List<CatelogyRevenueModel> mCatelogy;
    private IclickCategoryRevenue iclickCategory;
    public CatelogyRevenueAdapter(List<CatelogyRevenueModel> mCatelogy, IclickCategoryRevenue iclickCategory ) {
        this.mCatelogy = mCatelogy;
        this.iclickCategory=iclickCategory;
    }
    @NonNull
    @Override
    public CatelogyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_catelogy, parent,false);
        return new CatelogyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatelogyViewHolder holder, int position) {
        CatelogyRevenueModel catelogyModel = mCatelogy.get(position);
        if(catelogyModel==null){
            return;
        }
        holder.imgCatelogy.setImageResource(catelogyModel.getSourceImgCatelogy());
        holder.titleCatelogy.setText(catelogyModel.getTitleCatelogy());
        holder.lo_contain_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iclickCategory.onClickItemCategory(catelogyModel);
            }

        });
    }


    @Override
    public int getItemCount() {
        return mCatelogy.size();
    }

    public class CatelogyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgCatelogy;
        private TextView titleCatelogy;
        private LinearLayout lo_contain_ct;
        public CatelogyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCatelogy = itemView.findViewById(R.id.img_catelogy);
            titleCatelogy = itemView.findViewById(R.id.title_catelogy);
            lo_contain_ct = itemView.findViewById(R.id.lo_contain_ct);
        }
    }
}
