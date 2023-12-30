package com.example.william.my.module.demo.activity2

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.View
import android.view.Window
import com.example.william.my.lib.activity.BaseActivity
import com.example.william.my.module.demo.R

class TransitionSecondActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 允许使用transitions
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)
        val transition = intent.getStringExtra("transition")
        if (transition != null) {
            when (transition) {
                "explode" -> {
                    // 设置进入时进入动画
                    val explode = Explode()
                    explode.duration = 1000
                    window.enterTransition = explode
                }

                "slide" -> {
                    val slide = Slide()
                    slide.duration = 1000
                    window.enterTransition = slide
                }

                "fade" -> {
                    val fade = Fade()
                    fade.duration = 1000
                    window.enterTransition = fade
                }

                "share" -> {}
            }
        }
        // 所有操作在设置内容视图之前
        setContentView(R.layout.demo_activity_transition_second)
        findViewById<View>(R.id.transition_share).setOnClickListener {
            finishAfterTransition()
        }
    }
}