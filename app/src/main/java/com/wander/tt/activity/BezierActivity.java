package com.wander.tt.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.wander.base.widgetUtil.BezierEvaluator;
import com.wander.tt.R;

public class BezierActivity extends AppCompatActivity {

    private ImageView imageView;
    private WindowManager.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        layoutParams = getWindow().getAttributes();

        imageView = (ImageView) findViewById(R.id.bezier_image);
        Animator bezierAnimator = getBezierAnimator();
        bezierAnimator.start();

    }

    private Animator getBezierAnimator() {

        ValueAnimator animator = new ValueAnimator().ofObject(new BezierEvaluator(700, 1000)
                , new PointF(0, 0), new PointF(0, 1200));
//        animator.setInterpolator(new AccelerateInterpolator());
        animator.setTarget(imageView);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                imageView.setAlpha(1-animation.getAnimatedFraction());
            }
        });
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        return animator;


    }
}
