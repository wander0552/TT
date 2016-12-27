package com.wander.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wander.tt.R;

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
    private Bitmap mBitmap;
    private BitmapShader bitmapShader;
    private BitmapFactory.Options mOptions;

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
                mOptions = new BitmapFactory.Options();
                mOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.mipmap.twinkle_white, mOptions);
                int i = mOptions.outWidth / mViewWidth;
                int scale = i >0 ?i:1;
                mOptions.inJustDecodeBounds = false;
                mOptions.inSampleSize = scale;
                mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.twinkle_white, mOptions);

                mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, mHeight,
                        new int[]{0x00ffffff, 0xeeffffff, 0x00ffffff},
                        new float[]{0, 0.3f, 0.6f}, Shader.TileMode.CLAMP);
                bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                mPaint.setShader(bitmapShader);
                mMatrix = new Matrix();
                mRectF.set(0, 0, mViewWidth, mHeight);

            }
        }

    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMatrix != null) {


            // 设置一个矩阵
            mMatrix.setTranslate(mTranslate, mTranslate);
//            mLinearGradient.setLocalMatrix(mMatrix);
            bitmapShader.setLocalMatrix(mMatrix);


            canvas.drawRect(mRectF, mPaint);
//            canvas.drawOval(mRectF,mPaint);
            mTranslate += mViewWidth / 20;

            // 设置循环并指定时间
            if (mTranslate > mViewWidth * 2) {
                mTranslate = -mViewWidth;
            }
            postInvalidateDelayed(40);
        }
    }
}
