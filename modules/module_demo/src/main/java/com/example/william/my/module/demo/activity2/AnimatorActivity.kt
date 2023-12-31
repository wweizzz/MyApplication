package com.example.william.my.module.demo.activity2

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.CycleInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Demo.Animator)
class AnimatorActivity : BasicImageActivity() {

    private var index = -1

    private var animatorAlpha: ObjectAnimator? = null
    private var animatorRotation: ObjectAnimator? = null
    private var animatorScale: ObjectAnimator? = null
    private var animatorTranslation: ObjectAnimator? = null

    override fun initView() {
        super.initView()
        initImage()
    }

    private fun initImage() {
        mBinding.basicsImage.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    override fun onImageClick(view: View) {
        super.onImageClick(view)

        startAnimator()
    }

    private fun startAnimator() {
        index++
        if (index % 6 == 1) {
            startAnimatorAlpha()
        } else if (index % 6 == 2) {
            startAnimatorRotation()
        } else if (index % 6 == 3) {
            startAnimatorScale()
        } else if (index % 6 == 4) {
            startAnimatorTranslation()
        } else if (index % 6 == 5) {
            startAnimatorSet()
        } else if (index % 6 == 0) {
            startValueAnimator()
        }
    }

    /**
     * 透明度
     */
    private fun startAnimatorAlpha() {
        animatorAlpha = ObjectAnimator.ofFloat(mBinding.basicsImage, "alpha", 1f, 0f, 1f)
        animatorAlpha?.duration = 3000
        //alpha.setRepeatCount(ValueAnimator.INFINITE);//-1 代表无限循环执行
        animatorAlpha?.start()
    }

    /**
     * 旋转
     */
    private fun startAnimatorRotation() {
        animatorRotation = ObjectAnimator.ofFloat(mBinding.basicsImage, "rotation", 0f, 360f)
        animatorRotation?.duration = 3000
        animatorRotation?.start()
    }

    /**
     * 缩放
     */
    private fun startAnimatorScale() {
        animatorScale = ObjectAnimator.ofFloat(mBinding.basicsImage, "scaleX", 1f, 0.5f, 1f)
        animatorScale?.duration = 3000
        animatorScale?.start()
    }

    /**
     * 缩放
     */
    private fun startAnimatorTranslation() {
        animatorTranslation = ObjectAnimator.ofFloat(
            mBinding.basicsImage,
            "translationX",
            mBinding.basicsImage.translationX,
            -400f,
            mBinding.basicsImage.translationX
        )
        animatorTranslation?.duration = 3000
        animatorTranslation?.start()
    }

    /**
     * 动画组合
     */
    private fun startAnimatorSet() {
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(
            animatorAlpha,
            animatorRotation,
            animatorScale,
            animatorTranslation
        ) //如果上一个动画无线循环，则无法进行下一个动画
        animatorSet.duration = 3000
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                //animatorSet.start();
            }

            override fun onAnimationCancel(animation: Animator) {}

            /**
             * 当AnimatorSet重复时调用，由于AnimatorSet没有设置repeat的函数，所以这个方法永远不会被调用
             */
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    /**
     * 差值动画
     */
    private fun startValueAnimator() {
        val valueAnimator = ValueAnimator.ofFloat(0f, 360f)
        valueAnimator.duration = 3000
        valueAnimator.interpolator = AccelerateInterpolator() //加速
        valueAnimator.interpolator = DecelerateInterpolator() //减速
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.interpolator = LinearInterpolator() //匀速
        valueAnimator.interpolator = BounceInterpolator()
        valueAnimator.interpolator = CycleInterpolator(1f)

        //加速插值器，公式： y=t^(2f) （加速度参数. f越大，起始速度越慢，但是速度越来越快）
        //valueAnimator.setInterpolator(new AccelerateInterpolator(10));

        //减速插值器公式: y=1-(1-t)^(2f) （描述: 加速度参数. f越大，起始速度越快，但是速度越来越慢）
        //valueAnimator.setInterpolator(new DecelerateInterpolator());

        //先加速后减速插值器 y=cos((t+1)π)/2+0.5
        //valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        //张力值, 默认为2，T越大，初始的偏移越大，而且速度越快 公式：y=(T+1)×t3–T×t2
        //valueAnimator.setInterpolator(new AnticipateInterpolator());

        //张力值tension，默认为2，张力越大，起始和结束时的偏移越大，
        //而且速度越快;额外张力值extraTension，默认为1.5。公式中T的值为tension*extraTension
        //valueAnimator.setInterpolator(new AnticipateOvershootInterpolator());

        //弹跳插值器
        //valueAnimator.setInterpolator(new BounceInterpolator());

        //周期插值器 y=sin(2π×C×t)  周期值，默认为1；2表示动画会执行两次
        //valueAnimator.setInterpolator(new CycleInterpolator(2));

        //线性插值器，匀速公式：Y=T
        //valueAnimator.setInterpolator(new LinearInterpolator());

        //公式: y=(T+1)x(t1)3+T×(t1)2 +1
        //描述: 张力值，默认为2，T越大，结束时的偏移越大，而且速度越快
        valueAnimator.interpolator = OvershootInterpolator()
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            mBinding.basicsImage.rotation = value
        }
        valueAnimator.start()
    }
}