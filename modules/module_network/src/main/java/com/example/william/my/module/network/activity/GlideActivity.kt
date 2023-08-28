package com.example.william.my.module.network.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.basic.basic_repository.base.Constants
import com.example.william.my.module.network.glide.GlideApp

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
        GlideApp.with(this)
            .load(Constants.Url_Image2)
            .placeholder(R.drawable.ic_launcher)
            .error(R.drawable.ic_launcher)
            .centerCrop()
            .into(mBinding.basicsImage)
    }
}