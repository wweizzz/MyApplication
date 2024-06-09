package com.example.william.my.lib.utils

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.view.View

/**
 * 判断View是否可见
 */
object ViewCheckUtils {

    fun checkIsVisible(activity: Activity, view: View): Boolean {
        // 如果已经加载了，判断view是否显示
        val screenWidth = getScreenMetrics(activity).x
        val screenHeight = getScreenMetrics(activity).y
        val rect = Rect(0, 0, screenWidth, screenHeight)
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return view.getLocalVisibleRect(rect)
    }

    private fun getScreenMetrics(activity: Activity): Point {
        val metrics = activity.resources.displayMetrics
        return Point(metrics.widthPixels, metrics.heightPixels)
    }
}