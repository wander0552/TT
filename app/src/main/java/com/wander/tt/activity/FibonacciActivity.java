package com.wander.tt.activity;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.wander.tt.R;
import com.wander.tt.adapter.FibonacciAdapter;

public class FibonacciActivity extends AppCompatActivity {

    private LinearLayoutManager mLinearManager;
    private RecyclerView mRecyclerView;
    private FibonacciAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci);

        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.fibonacci_list);
        mProgressBar = (ProgressBar) findViewById(R.id.loading);
        mLinearManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearManager);
        mAdapter = new FibonacciAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mAdapter.isAsc()) {
                    int lastCompletelyVisibleItemPosition = mLinearManager.findLastCompletelyVisibleItemPosition();
                    if (lastCompletelyVisibleItemPosition == mAdapter.getItemCount() - 1) {
                        mAdapter.addDate(50);
                    }
                }else {
                    //// TODO: 2017/1/15 数据反转的情况

                }
            }
        });
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        findViewById(R.id.reverse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearManager.setReverseLayout(!mLinearManager.getReverseLayout());
            }
        });
        findViewById(R.id.reverse2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.reverse();
            }
        });

        mAdapter.setLoadingListener(new FibonacciAdapter.LoadListener() {
            @Override
            public void onStartLoad() {
                showLoading();

            }

            @Override
            public void onLoadSuc() {
                hideLoading();
            }
        });

    }

    private void hideLoading() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }


    private static class MyItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 30);
        }

    }
}
