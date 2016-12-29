package com.wander.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.wander.tt.R;
import com.wander.utils.ImageUtil;

/**
 * Created by wander on 2016/12/20.
 */

public class CDLightView extends View {

    private int mHeight;
    private int mWidth;
    private BitmapFactory.Options mOptions;
    private Bitmap mBg;
    private Bitmap cdLight;
    private Paint mPaint;
    private BitmapShader bgShader;
    private BitmapShader cdLightShader;
    private Matrix matrix;
    private int degree;
    private ColorMatrix colorMatrix;
    private ColorMatrixColorFilter colorMatrixFilter;
    private float gray;
    private float gray_speed;
    private Paint lightPaint;
    private Bitmap renBitmap;
    private BitmapShader renShader;
    private Matrix rMatrix;

    public CDLightView(Context context) {
        this(context, null);
    }

    public CDLightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CDLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        lightPaint = new Paint();
        matrix = new Matrix();
        rMatrix = new Matrix();
        colorMatrix = new ColorMatrix();
        gray = 0.8f;
        gray_speed = 0.1f;
    }

    private void initBitmap() {
        mBg = getScaleBitmap(R.mipmap.cd_full, mHeight);
        bgShader = new BitmapShader(mBg, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        cdLight = getScaleBitmap(R.mipmap.cd_guang, mHeight);
        cdLightShader = new BitmapShader(cdLight, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        renBitmap = getScaleBitmap(R.mipmap.meinv, mHeight);
        renBitmap = ImageUtil.zoomTo(renBitmap, 0.5f);
        renShader = new BitmapShader(renBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);


    }

    private Bitmap getScaleBitmap(int res, int height) {
        mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), res, mOptions);
        int i = mOptions.outWidth / height;
        int scale = i > 0 ? i : 1;
        mOptions.inJustDecodeBounds = false;
        mOptions.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res, mOptions);
//        bitmap = ImageUtil.zoomTo(bitmap,scale);
        return bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        initBitmap();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix != null) {
            matrix.setRotate(degree, mBg.getWidth() / 2, mBg.getHeight() / 2);
            bgShader.setLocalMatrix(matrix);


            rMatrix.setTranslate((mBg.getWidth() - renBitmap.getWidth()) / 2, (mBg.getHeight() - renBitmap.getHeight()) / 2);
            rMatrix.setRotate(degree, mBg.getWidth() / 2, mBg.getHeight() / 2);
            renShader.setLocalMatrix(rMatrix);

//            cdLightShader.setLocalMatrix(colorMatrix);
            lightPaint.setShader(cdLightShader);
//            colorMatrix.setSaturation(gray);
            colorMatrix.reset();
            float[] array = colorMatrix.getArray();
            array[4] = gray;
            array[9] = gray;
            array[14] = gray;
            colorMatrix.set(array);
            colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);



            //人
            mPaint.setShader(renShader);
            mPaint.setColorFilter(colorMatrixFilter);
            canvas.drawCircle(mBg.getWidth() / 2, mBg.getHeight() / 2, renBitmap.getHeight() / 2, mPaint);
            //cd
            mPaint.setShader(bgShader);
            canvas.drawCircle(mBg.getWidth() / 2, mBg.getHeight() / 2, mHeight / 2, mPaint);


            lightPaint.setColorFilter(colorMatrixFilter);
            canvas.drawCircle(mBg.getWidth() / 2, mBg.getHeight() / 2, mHeight / 2, lightPaint);
            degree += 1;
            gray += gray_speed;
            if (degree > 360) {
                degree = 0;
            }
            if (gray > 128 || gray < -127) {
                gray_speed *= -1;
            }
            postInvalidateDelayed(40);
        }
    }
}
