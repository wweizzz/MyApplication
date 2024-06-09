package com.example.william.my.lib.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.util.Log

/**
 * 5.0之前版本我们是通过系统发送的广播来监听的，即ConnectivityManager.CONNECTIVITY_ACTION
 * 5.0及之后版本我们可以通过ConnectivityManager.NetworkCallback这个类来监听
 *
 *
 * 7.0及以上静态注册广播不能收到"ConnectivityManager.CONNECTIVITY_ACTION"这个广播了
 */
@SuppressLint("MissingPermission", "ObsoleteSdkInt")
object NetworkChangeHelper {

    private val TAG = this.javaClass.simpleName

    private var mNetworkChangeListener: NetworkChangeListener? = null

    fun register(context: Context, networkChangeListener: NetworkChangeListener?) {
        mNetworkChangeListener = networkChangeListener
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*
             * 5.0以上使用ConnectivityManager.NetworkCallback
             */
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // 请注意这里会有一个版本适配bug，所以请在这里添加非空判断
            connectivityManager.requestNetwork(
                NetworkRequest.Builder().build(),
                NetworkCallbackImpl()
            )
        } else {
            /*
             * 5.0以下使用广播方式监听，即ConnectivityManager.CONNECTIVITY_ACTION
             */
            val receiver = NetworkChangeReceiver()
            val filter = IntentFilter()
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(receiver, filter)
        }
    }

    class NetworkChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val info = connectivityManager.activeNetworkInfo
                if (info != null) {
                    if (info.state == NetworkInfo.State.CONNECTED) {
                        if (ConnectivityManager.TYPE_WIFI == info.type) {
                            Log.i(TAG, "CONNECTIVITY_ACTION: 网络类型为WIFI")
                        } else if (ConnectivityManager.TYPE_MOBILE == info.type) {
                            Log.i(TAG, "CONNECTIVITY_ACTION: 网络类型为移动数据")
                        }
                        mNetworkChangeListener!!.onNetworkStatusChange(true)
                    } else {
                        mNetworkChangeListener!!.onNetworkStatusChange(false)
                    }
                }
            }
        }
    }

    private class NetworkCallbackImpl : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            mNetworkChangeListener!!.onNetworkStatusChange(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            mNetworkChangeListener!!.onNetworkStatusChange(false)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            //网络变化时，这个方法会回调多次
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i(TAG, "onCapabilitiesChanged: 网络类型为wifi")
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i(TAG, "onCapabilitiesChanged: 网络类型为移动数据")
                } else {
                    Log.i(TAG, "onCapabilitiesChanged: 其他网络")
                }
            }
        }
    }

    interface NetworkChangeListener {
        /**
         * 网络状态改变
         *
         * @param isAvailable 是否可用
         */
        fun onNetworkStatusChange(isAvailable: Boolean)
    }
}