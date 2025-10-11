package com.example.william.my.lib.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * App全局线程池
 * Global executor pools for the whole application.
 *
 *
 * 避免内存不足影响，例如磁盘读取不会延迟网络请求
 * Grouping tasks like this avoids the effects of task starvation
 * (e.g. disk reads don't wait behind webservice requests).
 */
object AppExecutorsHelper {

    /**
     * UI线程
     */
    private val mMain: Executor = MainThreadExecutor()

    /**
     * 磁盘IO线程
     */
    private val mDiskIO: ExecutorService = Executors.newSingleThreadExecutor()

    /**
     * 网络IO线程
     */
    private val mNetworkIO: ExecutorService = Executors.newFixedThreadPool(3)

    /**
     * 定时任务线程池
     */
    private val scheduledExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(
        Runtime.getRuntime().availableProcessors() * 3 + 2
    )

    fun main(): Executor {
        return mMain
    }

    fun diskIO(): ExecutorService {
        return mDiskIO
    }

    fun networkIO(): ExecutorService {
        return mNetworkIO
    }

    /**
     * 定时(延时)任务线程池
     * 替代Timer,执行定时任务,延时任务
     */
    fun scheduledExecutor(): ScheduledExecutorService {
        return scheduledExecutor
    }

    /**
     * MainThreadExecutor
     */
    private class MainThreadExecutor : Executor {

        override fun execute(command: Runnable) {
            Handler(Looper.getMainLooper()).post(command)
        }
    }
}