package com.example.william.my.module.opensource.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityBannerBinding
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

/**
 * https://github.com/youth5201314/banner
 */
@Route(path = ARouterPath.Opensource.Banner)
class BannerActivity : BaseVBActivity<OpenActivityBannerBinding?>() {

    override fun getViewBinding(): OpenActivityBannerBinding {
        return OpenActivityBannerBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initBanner()
    }

    private fun initBanner() {
        mBinding.banner.setAdapter(object : BannerImageAdapter<Int?>(listOf()) {
            override fun onBindView(
                holder: BannerImageHolder?,
                data: Int?,
                position: Int,
                size: Int
            ) {
                data?.let {
                    holder?.imageView?.setImageResource(it)
                }
            }
        })
            .addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(this)
    }
}