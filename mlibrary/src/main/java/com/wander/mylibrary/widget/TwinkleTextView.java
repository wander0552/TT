package com.wander.mylibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 备注：引用自网络，非常感谢！
 * @author ZeRo_Ci
 * @ClassName: TwinkleTextView
 * @Description: 自定义移动闪光textview
 * @date 2014-5-6 下午12:54:46
 */
public class TwinkleTextView extends TextView {
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mTranslate = 0;

    private boolean mAnimating = true;

    /**
     * @Description: 自定义移动闪光textview
     * @author ZeRo_Ci
     * @date 2014-5-6 下午12:55:05
     */
    public TwinkleTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            // 获取textView的宽度
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                // ↓↓↓关于LinearGradient的使用参考一下博客↓↓↓：
                // http://www.apihome.cn/api/android/LinearGradient.html
                // http://blog.csdn.net/q445697127/article/details/7865504

                mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
                        new int[]{0x33ffffff, 0xffffffff, 0x33ffffff},
                        new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
                // 设置对象
                mPaint.setShader(mLinearGradient);
                mMatrix = new Matrix();
            }

        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAnimating && mMatrix != null) {
            mTranslate += mViewWidth / 10;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            // 设置一个矩阵
            mMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mMatrix);
            // 设置循环并指定时间
            postInvalidateDelayed(50);
        }
    }
}