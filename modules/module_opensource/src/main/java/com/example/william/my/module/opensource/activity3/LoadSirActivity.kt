package com.example.william.my.module.opensource.activity3

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.opensource.loadsir.DefaultCallback
import com.example.william.my.module.opensource.loadsir.ErrorCallback
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

@Route(path = RouterPath.Opensource.LoadSir)
class LoadSirActivity : BasicResponseActivity() {

    private lateinit var loadService: LoadService<Any>

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initLoadService()
        sendToLoadService()
    }

    private fun initLoadService() {
        //loadService = LoadSir.getDefault().register(this) {
        //
        //}

        loadService = LoadSir.getDefault().register<Int>(this, {

        }, { httpResult ->
            var resultCode: Class<out Callback?> = Callback::class.java
            when (httpResult) {
                1 -> resultCode = DefaultCallback::class.java
                0 -> resultCode = ErrorCallback::class.java
            }
            resultCode
        })
    }

    private fun sendToLoadService() {
        //loadService.showSuccess() //成功回调
        //loadService.showCallback(CustomCallback::class.java) //其他回调

        loadService.showWithConvertor(1)
    }
}