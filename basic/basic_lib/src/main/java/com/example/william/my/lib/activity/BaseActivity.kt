package com.example.william.my.lib.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

open class BaseActivity : AppCompatActivity() {

    val tag: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(R.style.Basics_WindowAnimTheme_Slide)
        initARouter()

        initView(savedInstanceState)
        initView()

        initViewModel()
        observeViewModel()
    }

    open fun initView(savedInstanceState: Bundle?) {

    }

    open fun initView() {

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

    protected fun setStatusBarColor(): Int {
        return android.R.color.transparent
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