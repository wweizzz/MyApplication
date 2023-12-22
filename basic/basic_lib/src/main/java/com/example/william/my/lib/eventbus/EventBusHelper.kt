package com.example.william.my.lib.eventbus

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.meta.SubscriberInfoIndex

/**
 * kotlin:
 * /build/generated/sources/kapt/debug/…EventBusHelper
 * java:
 * /build/generated/ap_generated_sources/debug/out/…EventBusHelper
 */
class EventBusHelper private constructor() {

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBus(helper: EventBusHelper) {
        // 占位，只为了能生成 MyEventBusIndex 索引类
        // 如果项目中已经有用到 @Subscribe 去注解方法，这个方法可以直接删除
    }

    companion object {

        // EventBus 索引类
        private val subscriberInfoIndexList: MutableList<SubscriberInfoIndex> =
            mutableListOf()

        // 这个类是否需要注册 EventBus
        private val subscriberInfoIndexMap: MutableMap<String, MutableMap<String, Boolean>> =
            mutableMapOf()

        /**
         * 初始化 EventBus
         */
        fun init() {
            val builder = EventBus.builder()
            builder.ignoreGeneratedIndex(false) // 使用 Apt 插件
            for (subscriberInfoIndex in subscriberInfoIndexList) {
                builder.addIndex(subscriberInfoIndex) // 添加索引类
                subscriberInfoIndexMap[subscriberInfoIndex.javaClass.simpleName] = mutableMapOf()
            }
            builder.installDefaultEventBus() // 作为默认配置
        }

        fun addIndex(index: SubscriberInfoIndex): Companion {
            subscriberInfoIndexList.add(index)
            return this
        }

        fun isRegistered(subscriber: Any): Boolean {
            return EventBus.getDefault().isRegistered(subscriber)
        }

        /**
         * 注册 EventBus
         */
        fun register(subscriber: Any) {
            if (canSubscribeEvent(subscriber)) {
                if (!EventBus.getDefault().isRegistered(subscriber)) {
                    EventBus.getDefault().register(subscriber)
                }
            }
        }

        /**
         * 反注册 EventBus
         */
        fun unregister(subscriber: Any) {
            if (canSubscribeEvent(subscriber)) {
                if (EventBus.getDefault().isRegistered(subscriber)) {
                    EventBus.getDefault().unregister(subscriber)
                }
            }
        }

        //终止事件继续传递
        fun cancelDelivery(event: Any) {
            EventBus.getDefault().cancelEventDelivery(event)
        }

        //获取保存起来的粘性事件
        fun <T> getStickyEvent(classType: Class<T>): T {
            return EventBus.getDefault().getStickyEvent(classType)
        }

        //删除保存中的粘性事件
        fun removeStickyEvent(event: Any) {
            EventBus.getDefault().removeStickyEvent(event)
        }

        /**
         * 发送事件
         */
        fun postEvent(event: Any) {
            EventBus.getDefault().post(event)
        }

        /**
         * 发送粘性事件
         */
        fun postStickyEvent(event: Any) {
            EventBus.getDefault().postSticky(event)
        }

        /**
         * 判断是否使用了 EventBus 注解
         *
         * @param subscriber 被订阅的类
         */
        private fun canSubscribeEvent(subscriber: Any): Boolean {
            for (subscriberInfoIndex in subscriberInfoIndexList) {
                val subscriberInfoMap =
                    subscriberInfoIndexMap[subscriberInfoIndex.javaClass.simpleName]
                if (canSubscribeEvent(subscriber, subscriberInfoIndex, subscriberInfoMap!!)) {
                    return true
                }
            }
            return false
        }

        /**
         * 判断是否使用了 EventBus 注解
         *
         * @param subscriberInfo SubscriberInfoIndex
         * @param subscriber 被订阅的类
         */
        private fun canSubscribeEvent(
            subscriber: Any,
            subscriberInfo: SubscriberInfoIndex,
            subscriberInfoMap: MutableMap<String, Boolean>
        ): Boolean {
            var clazz: Class<*>? = subscriber.javaClass
            var result: Boolean? = subscriberInfoMap[subscriber.javaClass.simpleName]

            // 有的话直接返回结果
            if (result != null) {
                return result
            }

            // 没有的话进行遍历
            while (clazz != null) {
                // 如果索引集合中有这个 Class 类型的订阅信息，则这个类型的对象都需要注册 EventBus
                if (subscriberInfo.getSubscriberInfo(clazz) != null) {
                    // 这个类需要注册 EventBus
                    result = true
                    clazz = null
                } else {
                    // 跳过系统类（忽略 java. javax. android. androidx. 等开头包名的类）
                    clazz = if (clazz.name.startsWith("java") || clazz.name.startsWith("android")) {
                        null
                    } else {
                        clazz.superclass
                    }
                }
            }

            if (result == null) {
                // 这个类不需要注册 EventBus
                result = false
            }

            subscriberInfoMap[subscriber.javaClass.simpleName] = result
            subscriberInfoIndexMap[subscriberInfo.javaClass.simpleName] = subscriberInfoMap

            return result
        }
    }
}