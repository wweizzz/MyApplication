package com.example.william.my.lib.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar

open class BaseActivity : AppCompatActivity() {

    val tag: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        // 在 super.onCreate 之前请求窗口特性
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        super.onCreate(savedInstanceState)
        //setTheme(R.style.Basics_WindowAnimTheme_Slide)

        initARouter()
        initStatusBar()

        initViewBinding()
        initView(savedInstanceState)

        initViewModel()
        observeViewModel()
    }

    open fun initViewBinding() {

    }


    open fun initView(savedInstanceState: Bundle?) {

    }

    open fun initViewModel() {

    }

    open fun observeViewModel() {

    }

    override fun onStart() {
        super.onStart()
        //EventBusHelper.register(this)
    }

    override fun onStop() {
        super.onStop()
        //EventBusHelper.unregister(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        initARouter()
        setIntent(intent)
    }

    private fun initARouter() {
        ARouter.getInstance().inject(this)
    }

    protected open fun initStatusBar() {
        if (enableTransparentStatusBar()) {
            ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .statusBarDarkFont(false) //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(fitsSystemWindows()) //解决状态栏和布局重叠问题
                .keyboardEnable(true)
                .init()
        }
    }

    protected open fun enableTransparentStatusBar(): Boolean {
        return true
    }

    protected open fun fitsSystemWindows(): Boolean {
        return true
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}