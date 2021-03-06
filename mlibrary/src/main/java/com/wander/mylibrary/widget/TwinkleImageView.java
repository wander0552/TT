package com.wander.mylibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wander.mylibrary.R;

/**
 * Created by wander on 2016/12/14.
 */

public class TwinkleImageView extends ImageView {
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private Paint mPaint;
    private int mViewWidth;
    private int mTranslate;
    private int mHeight;
    private RectF mRectF;

    public TwinkleImageView(Context context) {
        this(context, null);
    }

    public TwinkleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwinkleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.twink_image, null);
//        ImageView mImageView = (ImageView) view.findViewById(R.id.twink_image);
        mPaint = new Paint();
        mRectF = new RectF();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
            if (mViewWidth > 0) {
                mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, mHeight,
                        new int[]{0x00ffffff, 0xeeffffff, 0x00ffffff},
                        new float[]{0, 0.3f, 0.6f}, Shader.TileMode.CLAMP);
//                BitmapShader bitmapShader = new BitmapShader();
                mPaint.setShader(mLinearGradient);
                mMatrix = new Matrix();
                mRectF.set(0, 0, mViewWidth, mHeight);

            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMatrix != null) {


            // 设置一个矩阵
            mTranslate = mViewWidth;
            mMatrix.setTranslate(mTranslate, mTranslate);
            mLinearGradient.setLocalMatrix(mMatrix);


            canvas.drawRect(mRectF, mPaint);
//            mTranslate += mViewWidth / 20;

            // 设置循环并指定时间
            if (mTranslate > mViewWidth * 2) {
                mTranslate = -mViewWidth;
            }
//            postInvalidateDelayed(50);
        }
    }
}
