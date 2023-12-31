package com.example.william.my.module.demo.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.databinding.DemoActivityViewFlipperBinding

@Route(path = RouterPath.Demo.ViewFlipper)
class ViewFlipperActivity : BaseVBActivity<DemoActivityViewFlipperBinding>() {

    override fun getViewBinding(): DemoActivityViewFlipperBinding {
        return DemoActivityViewFlipperBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        mBinding.viewFlipper.startFlipping()
    }

    override fun onStop() {
        super.onStop()
        mBinding.viewFlipper.stopFlipping()
    }
}