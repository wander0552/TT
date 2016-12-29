package com.wander.tt.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.wander.tt.R;
import com.wander.tt.adapter.TabAdapter;
import com.wander.tt.fragments.ItemFragment;
import com.wander.tt.fragments.dummy.DummyContent;

public class ScrollingActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.scroll_tab);
        mViewPager = (ViewPager) findViewById(R.id.scroll_viewPage);

        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Snackbar.make(fab,"同意请star",Snackbar.LENGTH_LONG).setAction("surprise", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScrollingActivity.this,"圣诞节快乐",Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
}
