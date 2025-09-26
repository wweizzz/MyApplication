package com.example.william.my.basic.basic_module.router.navigation

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.example.william.my.basic.basic_module.utils.Utils

/**
 * 跳转页面监听
 * Compiler An exception is encountered, [More than one interceptors use same priority [1], They are [LoginInterceptorImpl] and [LoginInterceptor].]
 */
class LoginNavigationImpl : NavigationCallback {

    private val TAG = "LoginNavigation"

    override fun onFound(postcard: Postcard) {
        println("找到了:" + postcard.path)
    }

    override fun onLost(postcard: Postcard) {
        println("找不到了:" + postcard.path)
    }

    override fun onArrival(postcard: Postcard) {
        println("跳转完了:" + postcard.path)
    }

    override fun onInterrupt(postcard: Postcard) {
        println("被拦截了:" + postcard.path)
    }

    private fun println(msg: String) {
        Utils.logcat(TAG, msg)
    }
}