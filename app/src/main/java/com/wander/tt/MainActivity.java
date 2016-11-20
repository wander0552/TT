package com.wander.tt;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.wander.tt.test.PermissionActivity;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private TextView tv;
    private ImageView imageView;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                startActivity(new Intent(MainActivity.this, PermissionActivity.class));
                                tv.setVisibility(View.GONE);
                            }
                        }).show();
            }
        });

        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        imageView = (ImageView) findViewById(R.id.image);
        tv.setText(stringFromJNI());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
            }
        });
        final TextView textView  = new TextView(this);
        textView.setText(stringFromJNI());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                layout.addView(textView);
                tv.setVisibility(View.VISIBLE);
            }
        });

        layout = (LinearLayout) findViewById(R.id.anim_layout);
        LayoutTransition layoutTransition = new LayoutTransition();


        ObjectAnimator animator = new ObjectAnimator();
        animator = animator.ofFloat(null,"alpha",0f,1.0f,1f).setDuration(5000);
//        animator.ofFloat(null, "rotationY", 0f, 360f,0f);

        animator.setDuration(5000);
        layoutTransition.setAnimator(LayoutTransition.APPEARING,animator);

        ObjectAnimator animator2 = new ObjectAnimator();
        animator2 = animator2.ofFloat(null,"alpha",1.0f,0f,1f).setDuration(5000);
        animator2.setDuration(5000);
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING,animator2);

        layoutTransition.setDuration(5000);
        layout.setLayoutTransition(layoutTransition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
