package com.example.william.my.core.eventbus.flow.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.whenStateAtLeast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.collections.set

class FlowEventBusModel : ViewModel() {

    //正常事件
    private val eventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    //粘性事件
    private val stickyEventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    private fun getEventFlow(eventName: String, isSticky: Boolean): MutableSharedFlow<Any> {
        return if (isSticky) {
            stickyEventFlows[eventName]
        } else {
            eventFlows[eventName]
        } ?: MutableSharedFlow<Any>(
            replay = if (isSticky) 1 else 0, // 重放数据个数
            extraBufferCapacity = Int.MAX_VALUE, // 额外缓存容量，避免挂起导致数据发送失败
            onBufferOverflow = BufferOverflow.SUSPEND // 缓存溢出策略
        ).also {
            if (isSticky) {
                stickyEventFlows[eventName] = it
            } else {
                eventFlows[eventName] = it
            }
        }
    }

    fun <T : Any> observeEvent(
        lifecycleOwner: LifecycleOwner,
        minState: Lifecycle.State,
        dispatcher: CoroutineDispatcher,
        eventName: String,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ): Job {
        return lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.lifecycle.whenStateAtLeast(minState) {
                getEventFlow(eventName, isSticky)
                    .collect { value ->
                        this.launch(dispatcher) {
                            invokeReceived(value, onReceived)
                        }
                    }
            }
        }
    }

    suspend fun <T : Any> observeEvent(
        eventName: String,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ) {
        getEventFlow(eventName, isSticky).collect { value ->
            invokeReceived(value, onReceived)
        }
    }

    fun postEvent(eventName: String, value: Any, timeMillis: Long) {
        listOfNotNull(getEventFlow(eventName, false), getEventFlow(eventName, true))
            .forEach { flow ->
                viewModelScope.launch {
                    delay(timeMillis)
                    flow.emit(value)
                }
            }
    }

    fun removeStickEvent(eventName: String) {
        stickyEventFlows.remove(eventName)
    }

    @ExperimentalCoroutinesApi
    fun clearStickEvent(eventName: String) {
        stickyEventFlows[eventName]?.resetReplayCache()
    }

    private fun <T : Any> invokeReceived(value: Any, onReceived: (T) -> Unit) {
        try {
            onReceived.invoke(value as T)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getEventObserverCount(eventName: String): Int {
        val stickyObserverCount = stickyEventFlows[eventName]?.subscriptionCount?.value ?: 0
        val normalObserverCount = eventFlows[eventName]?.subscriptionCount?.value ?: 0
        return stickyObserverCount + normalObserverCount
    }
}