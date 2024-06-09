package com.example.william.my.module.demo.proxy

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.ViewGroup

class HookActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private val activityNameSet: MutableSet<String> = HashSet()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {
        replaceProxyFrameLayout(activity)
    }

    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
    private fun replaceProxyFrameLayout(activity: Activity) {
        if (!activityNameSet.contains(activity.javaClass.name)) {
            val viewGroup = activity.window.decorView as ViewGroup
            val size = viewGroup.childCount
            val customFrameLayout = HookProxyFrameLayout(activity)
            for (i in 0 until size) {
                val view = viewGroup.getChildAt(i)
                if (view != null) {
                    viewGroup.removeView(view)
                    customFrameLayout.addView(view)
                }
            }
            viewGroup.addView(customFrameLayout)
            activityNameSet.add(activity.javaClass.name)
        }
    }
}