package com.example.william.my.lib.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class LazyFragment(private val layout: Int = 0) : Fragment() {

    /**
     * 是否已加载
     */
    private var isLoaded = false

    /**
     * 视图是否可见
     */
    private var isVisibleToUser = false

    /**
     * 视图是否可交互
     */
    private var isCallOnResume = false

    /**
     * 是否调用了setUserVisibleHint()方法
     * add show hide 不调用此方法，不执行懒加载
     */
    private var isCallUserVisibleHint = false

    override fun onResume() {
        super.onResume()
        isCallOnResume = true

        // 如未执行setUserVisibleHint，为add show hide模式
        // 使用isHidden()方法为isVisibleToUser赋值
        if (!isCallUserVisibleHint) {
            isVisibleToUser = !isHidden
        }
        judgeLazyInit()
    }

    /**
     * add show hide
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isVisibleToUser = !hidden
        judgeLazyInit()
    }

    /**
     * ViewPager
     */
    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        isCallUserVisibleHint = true
        judgeLazyInit()
    }

    private fun judgeLazyInit() {
        if (!isLoaded && isVisibleToUser && isCallOnResume) {
            isLoaded = true
            lazyInit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return if (layout != 0) {
            inflater.inflate(layout, container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    /**
     * 懒加载初始化
     */
    protected abstract fun lazyInit()
}