package com.example.william.my.lib.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Process
import java.util.Stack
import kotlin.system.exitProcess

object ActivityManagerUtil {

    private val TAG = this.javaClass.simpleName

    /**
     * 获取所有 Activity
     */
    private var allActivityStacks: Stack<Activity> = Stack()

    val currentActivity: Activity
        get() = allActivityStacks.lastElement()

    val currentActivityName: String
        get() = allActivityStacks.lastElement().localClassName

    /**
     * 获取指定类名的 Activity
     */
    fun getActivity(clazz: Class<*>): Activity? {
        for (activity in allActivityStacks) {
            if (clazz == activity.javaClass) {
                return activity
            }
        }
        return null
    }

    /**
     * 结束除当前传入以外所有 Activity
     */
    fun finishOthersActivity(cls: Class<*>) {
        for (activity in allActivityStacks) {
            if (activity.javaClass != cls) {
                activity.finish()
            }
        }
    }

    /**
     * 结束指定类名的 Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in allActivityStacks) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有 Activity
     */
    fun finishAllActivity() {
        for (activity in allActivityStacks) {
            activity.finish()
        }
        allActivityStacks.clear()
    }

    /**
     * 结束指定的 Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            if (!activity.isFinishing) {
                activity.finish()
                allActivityStacks.remove(activity)
            }
        }
    }

    /**
     * 退出 app 时调用
     */
    fun exitApp() {
        try {
            finishAllActivity()
            Process.killProcess(Process.myPid())
            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun register(mApplication: Application?) {
        mApplication?.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                allActivityStacks.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                allActivityStacks.remove(activity)
            }
        })
    }
}