package com.example.william.my.core.eventbus.livedata

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.william.my.core.eventbus.livedata.viewmodel.LiveEventBusModel
import kotlinx.coroutines.Job

object LiveEventBus {

//_______________________________________
//          observe event
//_______________________________________

    /**
     * 监听 Activity Scope 事件
     */
    @MainThread
    inline fun <reified T> observeEvent(
        owner: FragmentActivity,
        isSticky: Boolean = false,
        noinline onReceived: (T) -> Unit
    ): Job {
        return ViewModelProvider(owner)[LiveEventBusModel::class.java]
            .observeEvent(owner, T::class.java.name, isSticky, onReceived)
    }

    /**
     * 监听 Fragment Scope 事件
     */
    @MainThread
    inline fun <reified T> observeEvent(
        owner: Fragment,
        isSticky: Boolean = false,
        noinline onReceived: (T) -> Unit
    ): Job {
        return ViewModelProvider(owner)[LiveEventBusModel::class.java]
            .observeEvent(owner, T::class.java.name, isSticky, onReceived)
    }

//_______________________________________
//          post event
//_______________________________________

    /**
     * 限定范围的事件
     */
    inline fun <reified T> postEvent(scope: ViewModelStoreOwner, event: T) {
        ViewModelProvider(scope)[LiveEventBusModel::class.java]
            .postEvent(T::class.java.name, event!!)
    }

//_______________________________________
//          get event
//_______________________________________


    inline fun <reified T> removeStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
        ViewModelProvider(scope)[LiveEventBusModel::class.java]
            .removeStickEvent(event.name)
    }
}