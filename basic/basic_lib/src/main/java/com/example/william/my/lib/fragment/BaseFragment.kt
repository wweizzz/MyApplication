package com.example.william.my.lib.fragment

import android.os.Bundle
import android.util.Log
import android.view.View

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
        showState("onHiddenChanged : hidden : $hidden")
    }

    /**
     * ViewPager
     * 执行此方法
     * ViewPager2
     * 不执行此方法
     */
    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        showState("setUserVisibleHint : isVisibleToUser : $isVisibleToUser")
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
        showState("lazyInit")
    }

    override fun onResume() {
        super.onResume()
        showState("onResume")
    }

    private fun showState(state: String) {
        if (isDebug) Log.e("BaseFragment", state)
    }
}