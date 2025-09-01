package com.example.william.my.module.widget.activity

import android.os.Bundle
import android.widget.SeekBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.widget.databinding.DemoActivityBlurViewBinding

/**
 * 高斯模糊
 */
@Route(path = RouterPath.Widget.BlurView)
class BlurViewActivity : BaseVBActivity<DemoActivityBlurViewBinding>() {

    override fun getViewBinding(): DemoActivityBlurViewBinding {
        return DemoActivityBlurViewBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

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