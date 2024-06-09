package com.example.william.my.basic.basic_module.router.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Interceptor(priority = 1)
class LoginInterceptorImpl : IInterceptor {
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        if (postcard.extra == RouterPath.PERMISSION_LOGIN) {
            ARouter.getInstance().build("").navigation()
        } else {
            callback.onContinue(postcard)
        }
    }

    override fun init(context: Context) {}
}