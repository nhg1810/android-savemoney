package com.example.savemoneytime.MainApplication.FragmentInFragment.SecondFrag;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ListSpendingAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> title = new ArrayList<>();
    public ListSpendingAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
    public void addFragment(Fragment fragment, String Title){
        fragmentArrayList.add(fragment);
        title.add(Title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
