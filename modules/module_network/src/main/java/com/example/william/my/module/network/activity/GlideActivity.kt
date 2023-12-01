package com.example.william.my.module.network.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath

/**
 * https://bumptech.github.io/glide
 * https://github.com/bumptech/glide
 */
@Route(path = ARouterPath.Network.Glide)
class GlideActivity : BasicImageActivity() {

    override fun initView() {
        super.initView()

        load()
    }

    private fun load() {

    }
}