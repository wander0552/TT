package com.wander.tt.test;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wander.tt.R;

public class PermissionActivity extends AppCompatActivity {

    private int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
    }

    private void startCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PICTURE);
            }
            else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                } else {
                    applyPermission();
                }
            }
        }

    }

    private void applyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        switch (permission) {
            case Manifest.permission.CAMERA:
                showTips();
                return true;
            default:
                break;
        }

        return super.shouldShowRequestPermissionRationale(permission);
    }

    private void showTips() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("授权说明");
        alertDialog.setMessage("哥需要拍照了");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                applyPermission();
            }
        });
        alertDialog.show();
    }


}
