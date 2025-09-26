package com.example.william.my.lib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Criteria
import android.location.Geocoder
import android.location.GnssStatus
import android.location.GpsStatus
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.Locale

@SuppressLint("MissingPermission")
object LocationManagerUtils {

    private val TAG = this.javaClass.simpleName

    private var minTimeMs: Long = 1000L
    private var minDistanceM: Float = 1f

    private var mLocation: Location? = null

    private var mLocationManager: LocationManager? = null

    private val mCriteria: Criteria
        get() {
            val criteria = Criteria()
            // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
            criteria.accuracy = Criteria.ACCURACY_FINE
            // 设置是否要求速度
            criteria.isSpeedRequired = false
            // 设置是否允许运营商收费
            criteria.isCostAllowed = false
            // 设置是否需要方位信息
            criteria.isBearingRequired = false
            // 设置是否需要海拔信息
            criteria.isAltitudeRequired = false
            // 设置对电源的需求
            criteria.powerRequirement = Criteria.POWER_LOW
            return criteria
        }

    private val mBestProvider: String
        get() {
            return mLocationManager?.getBestProvider(mCriteria, true)
                ?: LocationManager.GPS_PROVIDER
        }

    fun startLocation(context: Context) {
        mLocationManager =
            context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 获取位置信息
        // 如果不设置查询要求，getLastKnownLocation() 方法传人的参数为 LocationManager.GPS_PROVIDER
        mLocation = mLocationManager?.getLastKnownLocation(mBestProvider)
        // 监听状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocationManager?.registerGnssStatusCallback(callback, Handler(Looper.getMainLooper()))
        } else {
            mLocationManager?.addGpsStatusListener(listener)
        }
        // 绑定监听，有4个参数
        // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
        // 参数2，位置信息更新周期，单位毫秒
        // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
        // 参数4，监听
        // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

        // 1秒更新一次，或最小位移变化超过1米更新一次；
        // 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
        mLocationManager?.requestLocationUpdates(
            mBestProvider, minTimeMs, minDistanceM, locationListener
        )
    }

    fun stopLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLocationManager?.registerGnssStatusCallback(callback)
        } else {
            mLocationManager?.removeGpsStatusListener(listener)
        }
        mLocationManager?.removeUpdates(locationListener)
        mLocationManager = null
        mLocation = null
    }

    private fun getAddress(context: Context, location: Location?): List<Address>? {
        var result: List<Address>? = null
        try {
            location?.let {
                val geocoder = Geocoder(context.applicationContext, Locale.getDefault())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(
                        it.latitude, it.longitude, 1
                    ) { addresses ->
                        result = addresses
                    }
                } else {
                    result = geocoder.getFromLocation(
                        it.latitude, it.longitude, 1
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun getLocalCity(context: Context, location: Location?): String? {
        if (location == null) {
            println("getLocalCity: 获取城市信息为空")
            return ""
        }
        val result = getAddress(context, location)
        var city: String? = ""
        city = if (!result.isNullOrEmpty()) {
            result[0].locality //获取城市
        } else {
            "获取不到城市信息"
        }
        return city
    }

    fun getAddressStr(context: Context, location: Location?): String? {
        if (location == null) {
            println("getAddressStr: 获取详细地址信息为空")
            return ""
        }
        val result = getAddress(context, location)
        var address: String? = ""
        address = if (!result.isNullOrEmpty()) {
            result[0].getAddressLine(0) //获取详细地址
        } else {
            "获取不到详细地址信息"
        }
        return address
    }

    fun getLocation(): Location? {
        if (mLocation == null) {
            println("getLocation: 获取当前位置信息为空")
            return null
        }
        return mLocation
    }

    fun getLocalCity(context: Context): String {
        return getLocalCity(context, mLocation) ?: ""
    }

    fun getAddressStr(context: Context): String {
        return getAddressStr(context, mLocation) ?: ""
    }

    fun addCallback(callback: LocationCallback?) {
        mLocationCallback = callback
    }

    fun removeCallback() {
        mLocationCallback = null
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private val callback: GnssStatus.Callback = object : GnssStatus.Callback() {
        // 定位启动
        override fun onStarted() {
            super.onStarted()
            println("定位启动")
        }

        // 定位结束
        override fun onStopped() {
            super.onStopped()
            println("定位结束")
        }

        // 第一次定位
        override fun onFirstFix(ttffMillis: Int) {
            super.onFirstFix(ttffMillis)
            println("第一次定位")
        }

    }

    private val listener = GpsStatus.Listener { event ->
        when (event) {
            GpsStatus.GPS_EVENT_FIRST_FIX -> {
                println("第一次定位")
            }

            GpsStatus.GPS_EVENT_SATELLITE_STATUS -> {

            }

            GpsStatus.GPS_EVENT_STARTED -> {
                println("定位启动")
            }

            GpsStatus.GPS_EVENT_STOPPED -> {
                println("定位结束")
            }
        }
    }

    // 位置监听
    private val locationListener: LocationListener = object : LocationListener {
        //位置信息变化时触发
        override fun onLocationChanged(location: Location) {
            mLocation = location
            mLocationCallback?.locationSuccess(location)
        }

        //GPS状态变化时触发
        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            when (status) {
                LocationProvider.AVAILABLE -> {
                    println("当前GPS状态为可见状态")
                }

                LocationProvider.OUT_OF_SERVICE -> {
                    println("当前GPS状态为服务区外状态")
                }

                LocationProvider.TEMPORARILY_UNAVAILABLE -> {
                    println("当前GPS状态为暂停服务状态")
                }
            }
        }

        //GPS开启时触发
        override fun onProviderEnabled(provider: String) {
            mLocation = mLocationManager?.getLastKnownLocation(provider)
        }

        //GPS禁用时触发
        override fun onProviderDisabled(provider: String) {
            mLocation = null
        }
    }

    private var mLocationCallback: LocationCallback? = null

    interface LocationCallback {
        fun locationSuccess(location: Location?)
    }

    private fun println(msg: String) {
        Log.e(TAG, msg)
    }
}