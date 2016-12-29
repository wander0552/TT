package com.wander.tt.bean;

import android.support.v4.app.Fragment;

/**
 * Created by wander on 2016/12/24.
 */

public class MyTabItem {
    private String title = "";
    private Fragment fragment;

    public MyTabItem(String s) {
        this.title = s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
