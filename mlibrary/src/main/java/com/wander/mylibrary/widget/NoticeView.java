package com.wander.mylibrary.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wander on 2016/10/23.
 */

public class NoticeView extends View {
    public NoticeView(Context context) {
        super(context);
    }

    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoticeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoticeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
