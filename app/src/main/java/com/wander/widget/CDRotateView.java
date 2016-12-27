package com.wander.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.wander.tt.R;

/**
 * Created by wander on 2016/12/19.
 */

public class CDRotateView extends View {

    private SweepGradient mSweepGradient;
    private Paint mPaint;

    public CDRotateView(Context context) {
        this(context, null);
    }

    public CDRotateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CDRotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        mSweepGradient = new SweepGradient(300, 300, new int[]{
                0, Color.WHITE,
                0, Color.WHITE,
                0, Color.WHITE,
                0},
                new float[]{
                        0, 0.25f,
                        0.25f, 0.5f,
                        0.5f, 0.75f,
                        0.75f});
        mPaint = new Paint();
        mPaint.setShader(mSweepGradient);
        mPaint.setAlpha(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(300, 300, 250, mPaint);
    }
}
