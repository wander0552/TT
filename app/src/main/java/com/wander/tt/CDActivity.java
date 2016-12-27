package com.wander.tt;

import android.app.WallpaperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CDActivity extends AppCompatActivity {
    String imageUrl = "http://d.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d22dc28839a442309f79052d265.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cd);


        ImageView rotateImage = (ImageView) findViewById(R.id.rotate_image);
        RotateAnimation rotateAnimation = new RotateAnimation(0,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(15000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateImage.startAnimation(rotateAnimation);

        ImageView cd = (ImageView) findViewById(R.id.cd_bg);
//        cd.startAnimation(rotateAnimation);


        ImageView light = (ImageView) findViewById(R.id.light);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.8f,1.0f);
        alphaAnimation.setDuration(8000);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        light.startAnimation(alphaAnimation);


    }
}
