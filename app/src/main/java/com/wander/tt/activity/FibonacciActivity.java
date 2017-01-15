package com.wander.tt.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.wander.tt.App;
import com.wander.tt.R;
import com.wander.tt.adapter.FibonacciAdapter;

public class FibonacciActivity extends AppCompatActivity {

    private LinearLayoutManager mLinearManager;
    private RecyclerView mRecyclerView;
    private FibonacciAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci);

        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.fibonacci_list);
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
                int lastCompletelyVisibleItemPosition = mLinearManager.findLastCompletelyVisibleItemPosition();
                if (lastCompletelyVisibleItemPosition == mAdapter.getItemCount() - 1) {
                    mAdapter.addDate(50);
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


    }

    private static class MyItemDecoration extends RecyclerView.ItemDecoration {

        private Paint paint;

        public MyItemDecoration() {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.RED);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 30);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            drawVertical(c, parent);
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            c.save();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = (int) (top + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 1, App.getInstance().getApplicationContext().getResources().getDisplayMetrics()));
                c.drawRect(left, top, right, bottom, paint);
            }
            c.restore();
        }
    }
}
