package com.example.william.my.module.opensource.oaid.utils;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SystemInfoUtil {

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统时间
     */
    public static String getSystemTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(System.currentTimeMillis());
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机制造商
     *
     * @return 手机制造商
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }
}
