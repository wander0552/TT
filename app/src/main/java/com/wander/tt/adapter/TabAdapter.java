package com.wander.tt.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wander.tt.bean.MyTabItem;
import com.wander.tt.fragments.ItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wander on 2016/12/24.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private List<MyTabItem> mList;

    public TabAdapter(FragmentManager fm) {
        super(fm);
        initData();
    }

    private void initData() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        for (int i = 0; i < 6; i++) {
            mList.add(new MyTabItem("wander tt" + i));
        }

    }

    @Override
    public Fragment getItem(int position) {
        MyTabItem item = mList.get(position);
        if (item != null){
            Fragment fragment = item.getFragment();
            if (fragment == null){
                item.setFragment(ItemFragment.newInstance(1,item.getTitle()));
            }
        }
        return item.getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        MyTabItem item = mList.get(position);
        if (item == null){
            return "wander";
        }
        return item.getTitle();
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }
}
