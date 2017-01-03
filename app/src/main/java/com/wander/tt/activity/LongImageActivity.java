package com.wander.tt.activity;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.wander.tt.R;

public class LongImageActivity extends AppCompatActivity {

    private SubsamplingScaleImageView imageView;
    private String tag =getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_image);

        testSub();

    }

    private void testSub() {
        imageView = (SubsamplingScaleImageView) findViewById(R.id.sub_image);
        imageView.setImage(ImageSource.resource(R.mipmap.img_long1));
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        imageView.setMinScale(1.0f);
        imageView.setMaxScale(5.0f);
        imageView.setDoubleTapZoomScale(1.0f);
        imageView.setDebug(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            Log.e(tag,"ACTION_UP");
        }
        return super.onTouchEvent(event);
    }
}
