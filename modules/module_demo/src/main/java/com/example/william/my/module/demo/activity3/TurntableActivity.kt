package com.example.william.my.module.demo.activity3

import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import java.util.Random

/**
 * 图片旋转动画之转盘功能
 */
@Route(path = RouterPath.Demo.Turntable)
class TurntableActivity : BasicImageActivity() {

    private var startDegree = 0 //初始角度

    override fun initView() {
        super.initView()

        initTurntable()
    }

    private fun initTurntable() {
        mBinding.basicsImage.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_launcher
            )
        )
        mBinding.basicsImage.setOnClickListener {
            goAward(Random().nextInt(10))
        }
    }

    private fun goAward(index: Int) {
        val n = 10 //奖品数目
        val lap = 3 //旋转圈数
        val one = 1000 //旋转一圈需要的时间
        val angle = index * 360 / n //奖品角度
        val increaseDegree = lap * 360 + angle //目标角度
        //初始化旋转动画，后面的四个参数是用来设置以自己的中心点为圆心转圈
        val rotateAnimation = RotateAnimation(
            startDegree.toFloat(),
            increaseDegree.toFloat(),
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f
        )
        //计算动画播放总时间index
        val time = ((lap + angle / 360) * one).toLong()
        //设置动画播放时间
        rotateAnimation.duration = time
        //设置动画播放完后，停留在最后一帧画面上
        rotateAnimation.fillAfter = true
        //设置动画的加速行为，是先加速后减速
        rotateAnimation.setInterpolator(
            this@TurntableActivity,
            android.R.anim.accelerate_decelerate_interpolator
        )
        //设置动画的监听器
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                //将最后的角度赋值给startDegree作为下次转圈的初始角度
                startDegree = angle
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        //开始播放动画
        mBinding.basicsImage.startAnimation(rotateAnimation)
    }
}