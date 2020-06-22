package com.vignesh.healthcare.doctor;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vignesh.healthcare.R;

public class UserDetailsTabAdapter extends FragmentStatePagerAdapter {
    Context context;
    int totalTabs;

    public UserDetailsTabAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context = context;
        this.totalTabs = 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UserDetailsTabFragment();
            case 1:
                return new UserRecordTabFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return context.getString(R.string.details);
        }else if(position == 1){
            return context.getString(R.string.record);
        }
        return super.getPageTitle(position);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }
}
