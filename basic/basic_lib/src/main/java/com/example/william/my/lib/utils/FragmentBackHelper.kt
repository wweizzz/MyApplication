package com.example.william.my.lib.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * 1. 在Activity中覆盖onBackPressed()方法
 * public void onBackPressed() {
 * if (!BackHandlerHelper.handleBackPress(this)) {
 * super.onBackPressed();}}
 * 2. 实现实现 FragmentBackHandler
 * public boolean onBackPressed() {
 * if (handleBackPressed) {
 * //外理返回键
 * return true;} else {
 * // 如果不包含子Fragment
 * // 或子Fragment没有外理back需求
 * // 可如直接 return false;
 * // 注：如果Fragment/Activity 中可以使用ViewPager 代替 this
 * return BackHandlerHelper.handleBackPress(this);}}
 */
object FragmentBackHelper {

    /**
     * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
     * 没有处理back事件，则尝试 FragmentManager.popBackStack()
     *
     * @return 如果处理了back键则返回 true
     */
    fun handleBackPress(fragmentManager: FragmentManager): Boolean {
        val fragments = fragmentManager.fragments
        if (fragments.isEmpty()) return false
        for (i in fragments.indices.reversed()) {
            val child = fragments[i]
            if (isFragmentBackHandled(child)) {
                return true
            }
        }
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    /**
     * 将back事件分发给Fragment中的子Fragment,
     * 该方法调用了 [.handleBackPress]
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun handleBackPress(fragment: Fragment): Boolean {
        return handleBackPress(fragment.childFragmentManager)
    }

    /**
     * 将back事件分发给Activity中的子Fragment,
     * 该方法调用了 [.handleBackPress]
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
        return handleBackPress(fragmentActivity.supportFragmentManager)
    }

    /**
     * 判断Fragment是否处理了Back键
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun isFragmentBackHandled(fragment: Fragment?): Boolean {
        return (fragment != null && fragment.isVisible
                && fragment.userVisibleHint //for ViewPager
                && fragment is FragmentBackHandler
                && (fragment as FragmentBackHandler).onBackPressed())
    }

    interface FragmentBackHandler {
        /**
         * activity back 键相应
         */
        fun onBackPressed(): Boolean
    }
}