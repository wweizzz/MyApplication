package com.example.william.my.lib.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar

open class BaseActivity : AppCompatActivity() {

    protected val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(R.style.Basics_WindowAnimTheme_Slide)

        initARouter()
        initEventBus()
        initStatusBar()

        initViewBinding()
        initView(savedInstanceState)

        initViewModel()
        observeViewModel()
    }

    override fun setContentView(view: View?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        super.setContentView(view)
    }

    override fun setContentView(layoutResID: Int) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        super.setContentView(layoutResID)
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

    private fun initEventBus() {

    }

    protected open fun initStatusBar() {
        if (enableTransparentStatusBar()) {
            ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .statusBarDarkFont(statusBarDarkFont()) //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(fitsSystemWindows()) //解决状态栏和布局重叠问题
                .keyboardEnable(true)
                .init()
        }
    }

    protected open fun enableTransparentStatusBar(): Boolean {
        return true
    }

    protected open fun statusBarDarkFont(): Boolean {
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