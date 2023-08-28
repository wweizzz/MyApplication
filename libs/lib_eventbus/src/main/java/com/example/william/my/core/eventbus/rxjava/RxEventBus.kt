package com.example.william.my.core.eventbus.rxjava

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import java.util.concurrent.ConcurrentHashMap

object RxEventBus {

    val mStickyBus: MutableMap<Class<*>, Any> = ConcurrentHashMap()
    val mRxEventBus: Subject<Any> = PublishSubject.create<Any>().toSerialized()

    /**
     * 发送事件
     */
    fun postEvent(obj: Any) {
        mRxEventBus.onNext(obj)
    }

    /**
     * 发送Sticky事件
     */
    fun postStickyEvent(event: Any) {
        synchronized(mStickyBus) {
            mStickyBus.put(event.javaClass, event)
        }
        postEvent(event)
    }

    /**
     * 根据传递的 eventType 类型返回特定类型 eventType 的被观察者
     */
    inline fun <reified T : Any> observeEvent(eventType: Class<T>): Observable<T> {
        synchronized(mStickyBus) {
            val event = mStickyBus[eventType]
            val observable = mRxEventBus.ofType(eventType)
            return if (event != null) {
                observable.mergeWith(Observable.create { subscriber: ObservableEmitter<T> ->
                    subscriber.onNext(eventType.cast(event)!!)
                })
            } else {
                observable
            }
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    fun <T> removeStickyEvent(eventType: Class<T>) {
        synchronized(mStickyBus) {
            eventType.cast(mStickyBus.remove(eventType))
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    fun removeAllStickyEvents() {
        synchronized(mStickyBus) {
            mStickyBus.clear()
        }
    }
}