package com.example.savemoneytime.MainApplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.savemoneytime.MainApplication.FragmentInFragment.FirstFrag.ExpensesFragment;
import com.example.savemoneytime.MainApplication.FragmentInFragment.FirstFrag.RevenueFragment;
import com.example.savemoneytime.MainApplication.FragmentInFragment.FirstFrag.SpendingAdapter;
import com.example.savemoneytime.R;
import com.google.android.material.tabs.TabLayout;

public class FirstFragment extends Fragment {
    View mySpendingFragment;
    ViewPager mviewPager;
    TabLayout mtabLayout;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mySpendingFragment= inflater.inflate(R.layout.fragment_first, container, false);


        //set theo id của view đó luôn
        mviewPager=mySpendingFragment.findViewById(R.id.view_pager_spending);
        mtabLayout = mySpendingFragment.findViewById(R.id.tab_layout_spending);
        mtabLayout.setupWithViewPager(mviewPager);

        SpendingAdapter spendingAdapter = new SpendingAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            spendingAdapter.addFragment(new ExpensesFragment(), "Chi ra");
        spendingAdapter.addFragment(new RevenueFragment(), "Đầu vào");

        mviewPager.setAdapter(spendingAdapter);

        return mySpendingFragment;
    }

}