package com.example.william.my.module.demo.activity2

import android.graphics.Typeface
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Demo.Typeface)
class TypefaceActivity : BasicResponseActivity() {

    override fun initView() {
        super.initView()
        initTypeface()
    }

    private fun initTypeface() {
        val typeface = Typeface.createFromAsset(assets, "fonts/juice.ttf")
        mBinding.basicsResponse.typeface = typeface
    }
}