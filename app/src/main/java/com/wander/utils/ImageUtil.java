package com.wander.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class ImageUtil {

    /**
     * 根据图片id保存图片到SD卡，从以前ImageManager中移出来
     *
     * @param mActivity
     * @param id
     * @param path
     */
    public static void saveImageToSD(Activity mActivity, String path, int id) {

        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(),
                id);
        saveBitmap(path, bitmap);
    }

    /**
     * 保存图片到sd卡，从以前ImageUtils中移出
     *
     * @param filePath
     * @param bitmap
     * @return
     */
    public static boolean saveBitmap(String filePath, Bitmap bitmap) {
        if (android.text.TextUtils.isEmpty(filePath) || bitmap == null) {
            return false;
        }
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        FileOutputStream fOutStream = null;
        try {
            fOutStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fOutStream != null) {
                try {
                    fOutStream.flush();
                    fOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * 保存裁剪后图片到SD卡返回路径，从以前ImageManager中移出来
     *
     * @param bitmap
     */
    public static String saveCroppedImage(Bitmap bitmap,File cropImage) {

        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(cropImage);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cropImage.getAbsolutePath();
    }

    /**
     * 从以前ImageManager中移出来，搞不懂为啥放个动画缩放在里面
     *
     * @param view
     */
    public static void showImageAnimation(ImageView view) {
        if (view != null && view.getAnimation() == null) {
            Animation a = new AlphaAnimation(0.4f, 1.0f);
            a.setDuration(300);
            view.startAnimation(a);
        }
    }


    /**
     * get 一个 根据path返回的图片。默认不缩放
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path) {
        return getBitmap(path, 1);
    }


    /**
     * get 一个 根据path，和传递的宽高缩放的图片
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmap(String path, int width, int height) {
        if (android.text.TextUtils.isEmpty(path)) {
            return null;
        }
        BitmapFactory.Options options = getBitmapOption(
                path, width, height);
        File file = new File(path);
        if (file.exists()) {
            return getBitmap(file, options);
        }
        return null;
    }


    /**
     * 加载本地路径图片
     *
     * @param path
     * @param inSampleSize,图片缩放比例，默认为1
     * @return
     */
    public static Bitmap getBitmap(String path, int inSampleSize) {
        if (android.text.TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (file.exists()) {
            return getBitmap(file, inSampleSize);
        }
        return null;
    }


    public static Bitmap getBitmap(Context context, int rid) {
        return getBitmap(context, rid, 1);
    }

    /**
     * 加载资源图片
     *
     * @param context
     * @param rid,资源id
     * @param inSampleSize，图片缩放比例，默认为1
     * @return
     */
    public static Bitmap getBitmap(Context context, int rid, int inSampleSize) {

        int sampleSize = inSampleSize;
        if (sampleSize < 1) {
            sampleSize = 1;
        }
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = sampleSize;

        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);
        } catch (OutOfMemoryError e) {
            options.inSampleSize = sampleSize * 2;
            bitmap = BitmapFactory.decodeResource(context.getResources(), rid, options);
        }
        return bitmap;
    }


    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap getBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } catch (OutOfMemoryError e) {
            return null;
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 旋转和缩放图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @param rotate
     * @return
     */
    public static Bitmap zoomAndRotateTo(Bitmap bitmap, int w, int h, float rotate) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);//缩放图片
        matrix.postRotate(rotate);//旋转图片
        Bitmap newbmp = null;
        try {
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (OutOfMemoryError e) {
        }

        return newbmp;
    }

    /**
     * 缩放图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomTo(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);//缩放图片
        Bitmap newbmp = null;
        try {
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (OutOfMemoryError e) {
        }

        return newbmp;
    }

    /**
     * 等比缩放
     *
     * @param bitmap
     * @param rate
     * @return
     */
    public static Bitmap zoomTo(Bitmap bitmap, float rate) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(rate, rate);//缩放图片

        Bitmap newbmp = null;
        try {
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (OutOfMemoryError e) {
        }
        return newbmp;
    }

    /**
     * 等比缩放到指定宽
     *
     * @param bitmap
     * @param width
     * @return
     */
    public static Bitmap zoomToWidth(Bitmap bitmap, int width) {
        int bmWidth = bitmap.getWidth();
        float rate = (float) width / (float) bmWidth;
        return zoomTo(bitmap, rate);
    }

    /**
     * 等比缩放到指定高
     *
     * @param bitmap
     * @param height
     * @return
     */
    public static Bitmap zoomToHeight(Bitmap bitmap, int height) {
        int bmHeight = bitmap.getHeight();
        float rate = (float) height / (float) bmHeight;
        return zoomTo(bitmap, rate);
    }

    /**
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomDrawable(Drawable drawable, int w, int h) {
        if (drawable == null)
            return null;
        Bitmap oldbmp = null;
        Bitmap newbmp;
        int width = drawable.getIntrinsicWidth(); // 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        try {
            oldbmp = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(oldbmp);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            newbmp = Bitmap.createScaledBitmap(oldbmp, w, h, true);
            return newbmp;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (oldbmp != null && !oldbmp.isRecycled())
                    oldbmp.recycle();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @param rotate
     * @return
     */
    public static Bitmap rotateTo(Bitmap bitmap, float rotate) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);//旋转图片
        Bitmap newbmp = null;
        try {
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (OutOfMemoryError e) {
        }
        return newbmp;
    }

    /**
     * 加载文件图片
     *
     * @param file
     * @param inSampleSize,图片缩放比例，默认为1
     * @return
     */
    private static Bitmap getBitmap(File file, int inSampleSize) {
        if (file == null) {
            return null;
        }
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        if (inSampleSize > 1) {
            opt.inSampleSize = inSampleSize;
        }

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opt);
        } catch (OutOfMemoryError e) {
        }
        return bitmap;
    }

    private static BitmapFactory.Options getBitmapOption(String file, int width,
                                                         int height) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        // 设置只是解码图片的边距，此操作目的是度量图片的实际宽度和高度
        BitmapFactory.decodeFile(file, opt);
        opt.inDither = false;
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        // 设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
//        opt.inSampleSize = PerformanceMgr.CalculateStrategyCommonBitmapSize(opt, width, height);

        opt.inJustDecodeBounds = false;// 最后把标志复原
        return opt;
    }


    private static Bitmap getBitmap(File file, BitmapFactory.Options ops) {
        if (file == null) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), ops);
        } catch (OutOfMemoryError e) {
        }
        return bitmap;
    }

}
