package com.example.william.my.lib.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * FragmentTransaction.setMaxLifecycle()
 */
abstract class NewLazyFragment(private val layout: Int = 0) : Fragment() {

    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!isLoaded && !isHidden) {
            isLoaded = true
            lazyInit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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