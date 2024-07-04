package com.example.william.my.module.demo.activity2

import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.demo.R
import com.example.william.my.module.demo.databinding.DemoActivityTransitionFirstBinding

/**
 * 视图过度动画
 */
@Route(path = RouterPath.Demo.TransitionFirst)
class TransitionFirstActivity : BaseVBActivity<DemoActivityTransitionFirstBinding>(),
    View.OnClickListener {

    override fun getViewBinding(): DemoActivityTransitionFirstBinding {
        return DemoActivityTransitionFirstBinding.inflate(layoutInflater)
    }

    private var mIntent: Intent? = null

    override fun initView() {
        super.initView()

        initIntent()

        initOnClick()
    }

    private fun initIntent() {
        mIntent = Intent(this, TransitionSecondActivity::class.java)
    }

    private fun initOnClick() {
        mBinding.transitionExplode.setOnClickListener(this)
        mBinding.transitionSlide.setOnClickListener(this)
        mBinding.transitionFade.setOnClickListener(this)
        mBinding.transitionShare.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.transition_explode -> { //分解
                mIntent?.putExtra("transition", "explode")
                startActivity(
                    mIntent,
                    ActivityOptions.makeSceneTransitionAnimation(this@TransitionFirstActivity)
                        .toBundle()
                )
            }

            R.id.transition_slide -> { //滑动
                mIntent?.putExtra("transition", "slide")
                startActivity(
                    mIntent,
                    ActivityOptions.makeSceneTransitionAnimation(this@TransitionFirstActivity)
                        .toBundle()
                )
            }

            R.id.transition_fade -> { //淡入
                mIntent?.putExtra("transition", "fade")
                startActivity(
                    mIntent,
                    ActivityOptions.makeSceneTransitionAnimation(this@TransitionFirstActivity)
                        .toBundle()
                )
            }

            R.id.transition_share -> { //共享元素
                //mIntent?.putExtra("transition", "share")
                //将原先的跳转改成如下方式，注意这里面的第三个参数决定了ActivityTwo 布局中的android:transitionName的值，它们要保持一致
                //startActivity(
                //    mIntent,
                //    ActivityOptions.makeSceneTransitionAnimation(
                //        this@TransitionFirstActivity, mBinding.transitionShare, "shareTransition"
                //    )
                //        .toBundle()
                //)
                ARouter.getInstance()
                    .build(RouterPath.Demo.TransitionSecond)
                    .withString("transition", "share")
                    .withOptionsCompat(
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this@TransitionFirstActivity,
                            //mBinding.transitionShare, "shareTransition"
                            Pair(mBinding.transitionShare, "shareTransition") // 多个共享元素
                        )
                    )
                    .navigation(this)
            }
        }
    }
}