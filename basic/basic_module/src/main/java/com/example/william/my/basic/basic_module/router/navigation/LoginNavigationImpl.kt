package com.example.william.my.basic.basic_module.router.navigation

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.example.william.my.lib.utils.Utils

/**
 * 跳转页面监听
 * Compiler An exception is encountered, [More than one interceptors use same priority [1], They are [LoginInterceptorImpl] and [LoginInterceptor].]
 */
class LoginNavigationImpl : NavigationCallback {

    private val TAG = "LoginNavigation"

    override fun onFound(postcard: Postcard) {
        Utils.d(TAG, "找到了:" + postcard.path)
    }

    override fun onLost(postcard: Postcard) {
        Utils.d(TAG, "找不到了:" + postcard.path)
    }

    override fun onArrival(postcard: Postcard) {
        Utils.d(TAG, "跳转完了:" + postcard.path)
    }

    override fun onInterrupt(postcard: Postcard) {
        Utils.d(TAG, "被拦截了:" + postcard.path)
    }
}