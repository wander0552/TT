package com.wander.tt;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wander.tt.activity.BezierActivity;
import com.wander.tt.activity.CDRotationActivity;
import com.wander.tt.activity.CustomScrollActivity;
import com.wander.tt.activity.FibonacciActivity;
import com.wander.tt.activity.LongImageActivity;
import com.wander.tt.activity.ScrollingActivity;
import com.wander.tt.test.PermissionActivity;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    private TextView tv;
    private ImageView imageView;
    private RelativeLayout layout;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mReference = FirebaseStorage.getInstance().getReference();

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
//    public native String stringFromJNI();
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
        }

    }

    private StorageReference mReference;

    private void fireUpload() {
//        /storage/5C96-26FD
        File file = new File("storage" + File.separator + "5C96-26FD" + File.separator + "DCIM" + File.separator + "20151206201628.jpg");
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            storageReference = mReference.child("images/20151206201628.jpg");
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.e(TAG, taskSnapshot.getDownloadUrl().getPath());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            });


        } else {
            Log.e(TAG, "file is not exists");
        }

    }

    private void fireDown() {
        try {
            File localfile = File.createTempFile("iamges", "jpg");
            if (storageReference != null) {
                storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.e(TAG, taskSnapshot.getStorage().getPath());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void jump(Class<?> cls) {
        startActivity(new Intent(this, cls));

    }
}
