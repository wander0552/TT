package com.wander.tt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wander.tt.activity.BezierActivity;
import com.wander.tt.activity.CDRotationActivity;
import com.wander.tt.activity.CustomScrollActivity;
import com.wander.tt.activity.FibonacciActivity;
import com.wander.tt.activity.KeystoreActivity;
import com.wander.tt.activity.LongImageActivity;
import com.wander.tt.activity.ScrollingActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    private TextView tv;
    private ImageView imageView;
    private RelativeLayout layout;

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
                                startActivity(new Intent(MainActivity.this, CDActivity.class));
//                                tv.setVisibility(View.GONE);
                            }
                        }).show();
            }
        });

        // Example of a call to a native method
    }


    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
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
    public void mainClick(View view) {
        switch (view.getId()) {
            case R.id.scroll_test:
                startActivity(new Intent(this, ScrollingActivity.class));
                break;
            case R.id.cd_rotation:
                startActivity(new Intent(this, CDRotationActivity.class));
                break;
            case R.id.custom_scroll:
                startActivity(new Intent(this, CustomScrollActivity.class));
                break;
            case R.id.bezier_test:
                startActivity(new Intent(this, BezierActivity.class));
                break;
            case R.id.long_image:
                jump(LongImageActivity.class);
                break;
            case R.id.firebase_down:
                fireDown();
                break;
            case R.id.firebase_upload:
                fireUpload();
                break;
            case R.id.fibonacci_test:
                startActivity(new Intent(this, FibonacciActivity.class));
                break;
            case R.id.keystore:
                startActivity(new Intent(this, KeystoreActivity.class));
                break;
        }

    }


    private void fireUpload() {

    }

    private void fireDown() {

    }

    private void jump(Class<?> cls) {
        startActivity(new Intent(this, cls));

    }
}
