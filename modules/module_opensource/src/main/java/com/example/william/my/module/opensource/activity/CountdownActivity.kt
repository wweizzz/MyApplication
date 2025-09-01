package com.example.william.my.module.opensource.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityCountdownBinding

/**
 * https://github.com/iwgang/CountdownView
 *
 * add ' tools:replace="android:allowBackup,android:label" ' to <application>
 */
@Route(path = RouterPath.Opensource.CountdownView)
class CountdownActivity : BaseVBActivity<OpenActivityCountdownBinding>() {

    override fun getViewBinding(): OpenActivityCountdownBinding {
        return OpenActivityCountdownBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initCountdownView()
    }

    private fun initCountdownView() {
        mBinding.countdownView.start(995550000) // Millisecond
        for (time in 0..999) {
            mBinding.countdownView.updateShow(time.toLong())
        }
    }
}