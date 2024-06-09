package com.example.william.my.core.eventbus.livedata.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * https://tech.meituan.com/2018/07/26/android-livedatabus.html
 */
class BusMutableLiveData<T> : MutableLiveData<T>() {

    private val observerMap: MutableMap<Observer<in T>, Observer<in T>> = HashMap()

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        try {
            hook(observer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeForever(observer: Observer<in T>) {
        if (!observerMap.containsKey(observer)) {
            observerMap[observer] = BusObserverWrapper(observer)
        }
        super.observeForever(observerMap[observer]!!)
    }

    override fun removeObserver(observer: Observer<in T>) {
        val realObserver: Observer<in T>? =
            if (observerMap.containsKey(observer)) {
                observerMap.remove(observer)
            } else {
                observer
            }
        super.removeObserver(realObserver!!)
    }

    @Throws(Exception::class)
    private fun hook(observer: Observer<in T>) {
        //get wrapper's version
        val clazz = LiveData::class
        val fieldObservers = clazz.java.getDeclaredField("mObservers")
        fieldObservers.isAccessible = true
        val objectObservers = fieldObservers[this]
        val classObservers: Class<*> = objectObservers.javaClass
        val methodGet = classObservers.getDeclaredMethod("get", Any::class.java)
        methodGet.isAccessible = true
        val objectWrapperEntry = methodGet.invoke(objectObservers, observer)
        var objectWrapper: Any? = null
        if (objectWrapperEntry is Map.Entry<*, *>) {
            objectWrapper = objectWrapperEntry.value
        }
        if (objectWrapper == null) {
            throw NullPointerException("Wrapper can not be bull!")
        }
        val classObserverWrapper: Class<in Any> = objectWrapper.javaClass.superclass!!
        val fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion")
        fieldLastVersion.isAccessible = true
        //get livedata's version
        val fieldVersion = clazz.java.getDeclaredField("mVersion")
        fieldVersion.isAccessible = true
        val objectVersion = fieldVersion[this]
        //set wrapper's version
        fieldLastVersion[objectWrapper] = objectVersion
    }
}