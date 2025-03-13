package com.example.william.my.module.libraries.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.eventbus.rxjava.RxEventBus
import com.example.william.my.module.libraries.event.GlobalEvent
import com.example.william.my.module.libraries.event.StickyEvent

/**
 * 延迟发送    有序接收    Sticky    生命感知    可跨进程    线程分发
 * ❌        ✅        ✅        ❌        ❌        ✅
 */
@Route(path = RouterPath.Libraries.RxEventBus)
class RxEventBusActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "observeRxEventBus",
            "postRxEventBus",
            "postStickyRxEventBus",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                observeRxBusEvent()
            }

            1 -> {
                RxEventBus.postEvent(
                    GlobalEvent("RxEventBus post by Activity")
                )
            }

            2 -> {
                RxEventBus.postStickyEvent(
                    StickyEvent("RxEventBus postSticky by Activity")
                )
            }

        }
    }

    private fun observeRxBusEvent() {
        val globalDisposable = RxEventBus.observeEvent(GlobalEvent::class.java).subscribe {
            showEventMessage(it.message)
        }

        val stickyDisposable = RxEventBus.observeEvent(StickyEvent::class.java).subscribe {
            showEventMessage(it.message)
        }
        showEventMessage("RxEventBus observe")
    }
}