package com.example.william.my.lib.utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Handler弱引用工具类
 */
object HandlerUtils {

    init {
        throw UnsupportedOperationException("Guy, r u crazy? u can NOT instantiate me...")
    }

    class HandlerHolder : Handler {

        private val weakReference: WeakReference<OnReceiveMessageHandler>

        /**
         * 使用必读：推荐在Activity或者Activity内部持有类中实现该接口，不要使用匿名类，可能会被GC
         *
         * @param handler 收到消息回调接口
         */
        @Suppress("DEPRECATION")
        constructor(handler: OnReceiveMessageHandler) {
            weakReference = WeakReference(handler)
        }

        constructor(looper: Looper, handler: OnReceiveMessageHandler) : super(looper) {
            weakReference = WeakReference(handler)
        }

        override fun handleMessage(msg: Message) {
            weakReference.get()?.handlerMessage(msg)
        }
    }

    /**
     * 收到消息回调接口
     */
    interface OnReceiveMessageHandler {
        /**
         * 处理消息
         *
         * @param msg
         */
        fun handlerMessage(msg: Message)
    }
}