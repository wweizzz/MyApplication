package com.example.william.my.module.sample.activity.topic

import android.content.SharedPreferences
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.module.sample.hitl.simple.Car
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Hilt
 * https://developer.android.google.cn/training/dependency-injection/hilt-android
 */
@AndroidEntryPoint // 将依赖项注入 Android 类
@Route(path = ARouterPath.Sample.Hilt)
class HiltActivity : BasicResponseActivity() {

    @Inject
    lateinit var car: Car

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun initView() {
        super.initView()

        car.drive()
    }
}