package com.example.william.my.module.demo.activity2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity;
import com.example.william.my.basic.basic_module.router.path.RouterPath;
import com.example.william.my.basic.basic_module.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请
 */
@Route(path = RouterPath.Demo.Permission)
public class PermissionActivity extends BasicResponseActivity {

    private final String[] permissions = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,//日历

            Manifest.permission.CAMERA,//相机

            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,//联系人

            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,//位置信息

            Manifest.permission.RECORD_AUDIO,//麦克风

            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,//手机权限

            Manifest.permission.BODY_SENSORS,//传感器

            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,//短信

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE//存储
    };


    @Override
    public void onResponseClick(View view) {

        //if (ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        //    //Fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, position);
        //    ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.CAMERA}, 1000);
        //}

        List<String> mPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);
            }
        }
        if (!mPermissionList.isEmpty()) {
            String[] permissions = mPermissionList.toArray(new String[0]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, 1000);
        }
    }

    /**
     * 用户选择允许或拒绝后调用此方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //一键申请
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                // 同意权限申请
                Utils.INSTANCE.toast("同意权限申请");
            }
        }
    }
}