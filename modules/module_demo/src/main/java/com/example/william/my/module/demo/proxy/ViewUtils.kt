package com.example.william.my.module.demo.proxy

import android.util.Log
import android.view.View
import android.view.ViewGroup

object ViewUtils {

    @JvmStatic
    fun getAbsolutePath(view: View?): String {
        if (view == null) {
            return ""
        }
        if (view.parent == null) {
            return "rootView"
        }
        var temp: View = view
        val path = StringBuilder()
        while (temp.parent != null && temp.parent is View) {
            var index = 0
            try {
                index = indexOfChild(temp.parent as ViewGroup, temp)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            path.insert(0, temp.javaClass.simpleName + '[' + index + "]/")
            temp = temp.parent as View
        }
        return path.substring(0, path.length - 1)
    }

    private fun indexOfChild(parent: ViewGroup?, child: View): Int {
        if (parent == null) {
            return 0
        }
        val count = parent.childCount
        var j = 0
        for (i in 0 until count) {
            val view = parent.getChildAt(i)
            if (child.javaClass.isInstance(view)) {
                if (view === child) {
                    return j
                }
                j++
            }
        }
        return -1
    }

    @JvmStatic
    fun logcat(tag: String, msg: String) {
        Log.d(tag, msg)
    }
}