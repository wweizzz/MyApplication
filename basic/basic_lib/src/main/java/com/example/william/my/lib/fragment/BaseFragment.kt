package com.example.william.my.lib.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.william.my.lib.eventbus.EventBusHelper

/**
 * add show hide
 * 当使用旧方式适配时，Fragment通过onHiddenChanged()方法
 * 当使用新方式适配时，Fragment通过onHiddenChanged()方法或者onResume()方法()
 * ViewPager
 * 当使用旧方式适配时，Fragment通过setUserVisibleHint()方法
 * 当使用新方式适配时，Fragment通过setUserVisibleHint()方法
 * ViewPager2
 * 当使用旧方式适配时，Fragment通过onResume()，onPause()方法
 * 当使用新方式适配时，Fragment通过onResume()，onPause()方法
 */
open class BaseFragment(layout: Int = 0) : NewLazyFragment(layout) {

    private val isDebug = false

    /**
     * add show hide
     * 当使用旧方式适配时，执行此方法
     * 当使用新方式适配时，也执行此方法
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        println("onHiddenChanged : hidden : $hidden")
    }

    /**
     * ViewPager
     * 执行此方法
     * ViewPager2
     * 不执行此方法
     */
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        println("setUserVisibleHint : isVisibleToUser : $isVisibleToUser")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view, savedInstanceState)
        initView(view)

        initViewModel()
        observeViewModel()
    }

    /**
     * 在此方法内初始化控件
     */
    open fun initView(view: View?, state: Bundle?) {

    }

    /**
     * 在此方法内初始化控件
     */
    open fun initView(view: View) {

    }

    /**
     * 在此方法内初始化ViewModel
     */
    open fun initViewModel() {

    }

    /**
     * 在此方法内监听ViewModel
     */
    open fun observeViewModel() {

    }

    /**
     * 懒加载，只执行一次
     */
    override fun lazyInit() {
        println("lazyInit")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onStart() {
        super.onStart()
        EventBusHelper.register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBusHelper.unregister(this)
    }

    private fun println(msg: String) {
        if (isDebug) Log.e(tag, msg)
    }
}