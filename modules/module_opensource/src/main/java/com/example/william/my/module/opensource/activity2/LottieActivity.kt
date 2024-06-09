package com.example.william.my.module.opensource.activity2

import android.animation.Animator
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.databinding.OpenActivityLottieBinding

/**
 * https://github.com/airbnb/lottie-android
 */
@Route(path = RouterPath.Opensource.Lottie)
class LottieActivity : BaseVBActivity<OpenActivityLottieBinding>() {

    override fun getViewBinding(): OpenActivityLottieBinding {
        return OpenActivityLottieBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initLottieAnim()
    }

    private fun initLottieAnim() {
        mBinding.lottie.addAnimatorListener(
            object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }
        )
    }
}