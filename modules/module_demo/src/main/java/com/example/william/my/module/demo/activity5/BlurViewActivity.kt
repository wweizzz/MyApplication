package com.example.william.my.module.demo.activity5

import android.widget.SeekBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseVBActivity
import com.example.william.my.module.demo.databinding.DemoActivityBlurBinding

/**
 * 高斯模糊
 */
@Route(path = ARouterPath.Demo.BlurView)
class BlurViewActivity : BaseVBActivity<DemoActivityBlurBinding>() {

    override fun getViewBinding(): DemoActivityBlurBinding {
        return DemoActivityBlurBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initBlurView()
    }

    private fun initBlurView() {
        mBinding.blurSeekBar.max = 100
        mBinding.blurBlurView.setImageView(R.drawable.ic_launcher)
        mBinding.blurSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mBinding.blurBlurView.setImageBlur(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}