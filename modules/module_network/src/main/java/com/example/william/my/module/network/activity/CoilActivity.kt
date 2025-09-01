package com.example.william.my.module.network.activity

import android.os.Bundle
import coil.load
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath

/**
 * https://github.com/coil-kt/coil
 * 圆形变换（CircleCropTransformation）和圆角变换（RoundedCornersTransformation）
 */
@Route(path = RouterPath.Network.Coil)
class CoilActivity : BasicImageActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        load()
    }

    private fun load() {
        mBinding.basicsImage.load(Constants.Url_Image1)
    }
}