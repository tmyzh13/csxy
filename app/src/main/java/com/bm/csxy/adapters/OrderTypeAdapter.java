package com.bm.csxy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bm.csxy.view.order.OrderTypeChildFragment;

/**
 * Created by john on 2017/11/7.
 */

public class OrderTypeAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"全部", "待付款","待评价","退款"};

    public OrderTypeAdapter (FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return OrderTypeChildFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
