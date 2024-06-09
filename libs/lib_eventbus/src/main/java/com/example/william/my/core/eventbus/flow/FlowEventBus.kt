package com.example.william.my.core.eventbus.flow

import android.app.Application
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.william.my.core.eventbus.flow.store.FlowEventBusProvider
import com.example.william.my.core.eventbus.flow.viewmodel.FlowEventBusModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * https://github.com/biubiuqiu0/flow-event-bus
 */
object FlowEventBus {

    private lateinit var app: Application

    fun getApp(): Application {
        if (!this::app.isInitialized) {
            throw Exception("")
        }
        return app
    }

    fun init(app: Application) {
        FlowEventBus.app = app
    }

//_______________________________________
//          observe event
//_______________________________________

    /**
     * 监听 App Scope 事件
     */
    @MainThread
    inline fun <reified T> observeEvent(
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        minState: Lifecycle.State = Lifecycle.State.STARTED,
        isSticky: Boolean = false,
        noinline onReceived: (T) -> Unit
    ): Job {
        return FlowEventBusProvider[FlowEventBusModel::class.java]
            .observeEvent(
                ProcessLifecycleOwner.get(),
                minState,
                dispatcher,
                T::class.java.name,
                isSticky,
                onReceived
            )
    }

    /**
     * 监听 Activity Scope 事件
     */
    @MainThread
    inline fun <reified T> observeEvent(
        owner: FragmentActivity,
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        minState: Lifecycle.State = Lifecycle.State.STARTED,
        isSticky: Boolean = false,
        noinline onReceived: (T) -> Unit
    ): Job {
        return ViewModelProvider(owner)[FlowEventBusModel::class.java]
            .observeEvent(owner, minState, dispatcher, T::class.java.name, isSticky, onReceived)
    }

    /**
     * 监听 Fragment Scope 事件
     */
    @MainThread
    inline fun <reified T> observeEvent(
        owner: Fragment,
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        minState: Lifecycle.State = Lifecycle.State.STARTED,
        isSticky: Boolean = false,
        noinline onReceived: (T) -> Unit
    ): Job {
        return ViewModelProvider(owner)[FlowEventBusModel::class.java]
            .observeEvent(owner, minState, dispatcher, T::class.java.name, isSticky, onReceived)
    }

    /**
     * 监听 App CoroutineScope 事件
     */
    @MainThread
    inline fun <reified T> observeEvent(
        coroutineScope: CoroutineScope,
        isSticky: Boolean = false,
        noinline onReceived: (T) -> Unit
    ): Job {
        return coroutineScope.launch {
            FlowEventBusProvider[FlowEventBusModel::class.java]
                .observeEvent(T::class.java.name, isSticky, onReceived)
        }
    }

    /**
     * 监听 ViewModelStoreOwner CoroutineScope 事件
     */
    @MainThread
    inline fun <reified T> observeEvent(
        owner: ViewModelStoreOwner,
        coroutineScope: CoroutineScope,
        isSticky: Boolean = false,
        noinline onReceived: (T) -> Unit
    ): Job {
        return coroutineScope.launch {
            ViewModelProvider(owner)[FlowEventBusModel::class.java]
                .observeEvent(T::class.java.name, isSticky, onReceived)
        }
    }

//_______________________________________
//          post event
//_______________________________________

    /**
     * Application范围的事件
     */
    inline fun <reified T> postEvent(event: T, timeMillis: Long = 0L) {
        FlowEventBusProvider[FlowEventBusModel::class.java]
            .postEvent(T::class.java.name, event!!, timeMillis)
    }

    /**
     * 限定范围的事件
     */
    inline fun <reified T> postEvent(scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L) {
        ViewModelProvider(scope)[FlowEventBusModel::class.java]
            .postEvent(T::class.java.name, event!!, timeMillis)
    }

//_______________________________________
//          get event
//_______________________________________

    //获取事件
    inline fun <reified T> getEventObserverCount(event: Class<T>): Int {
        return FlowEventBusProvider[FlowEventBusModel::class.java]
            .getEventObserverCount(event.name)
    }

    inline fun <reified T> getEventObserverCount(scope: ViewModelStoreOwner, event: Class<T>): Int {
        return ViewModelProvider(scope)[FlowEventBusModel::class.java]
            .getEventObserverCount(event.name)
    }

    //移除事件
    inline fun <reified T> removeStickyEvent(event: Class<T>) {
        FlowEventBusProvider[FlowEventBusModel::class.java]
            .removeStickEvent(event.name)
    }

    inline fun <reified T> removeStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
        ViewModelProvider(scope)[FlowEventBusModel::class.java]
            .removeStickEvent(event.name)
    }

    // 清除事件缓存
    @ExperimentalCoroutinesApi
    inline fun <reified T> clearStickyEvent(event: Class<T>) {
        FlowEventBusProvider[FlowEventBusModel::class.java]
            .clearStickEvent(event.name)
    }

    @ExperimentalCoroutinesApi
    inline fun <reified T> clearStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
        ViewModelProvider(scope)[FlowEventBusModel::class.java]
            .clearStickEvent(event.name)
    }
}