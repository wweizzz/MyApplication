package com.example.william.my.basic.basic_module.router.navigation

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.example.william.my.basic.basic_module.utils.Utils

/**
 * 登录拦截器
 * Compiler An exception is encountered, [More than one interceptors use same priority [1], They are [LoginInterceptorImpl] and [LoginInterceptor].]
 */
@Interceptor(priority = 100)
class LoginInterceptor : IInterceptor {

    private val TAG = "LoginInterceptor"

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        println("process:" + postcard.path)
        callback.onContinue(postcard)
    }

    override fun init(context: Context) {}

    private fun println(msg: String) {
        Utils.logcat(TAG, msg)
    }
}
