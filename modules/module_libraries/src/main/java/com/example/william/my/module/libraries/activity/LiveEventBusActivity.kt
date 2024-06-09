package com.example.william.my.module.libraries.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.eventbus.livedata.LiveEventBus
import com.example.william.my.module.libraries.event.GlobalEvent
import com.example.william.my.module.libraries.event.StickyEvent

/**
 * 延迟发送    有序接收    Sticky    生命感知    可跨进程    线程分发
 * ✅        ✅        ✅        ✅        ✅        ❌
 */
@Route(path = RouterPath.Libraries.LiveEventBus)
class LiveEventBusActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "observeLiveEventBus",
            "postLiveEventBus",
            "postStickyLiveEventBus",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                observeLiveEventBus()
            }

            1 -> {
                LiveEventBus.postEvent(
                    this,
                    GlobalEvent("LiveEventBus post by Activity")
                )
            }

            2 -> {
                LiveEventBus.postEvent(
                    this,
                    StickyEvent("LiveEventBus postSticky by Activity")
                )
            }
        }
    }

    private fun observeLiveEventBus() {
        LiveEventBus.observeEvent<GlobalEvent>(this) {
            addMessage(it.message)
        }
        LiveEventBus.observeEvent<StickyEvent>(this, isSticky = true) {
            addMessage(it.message)
        }
        addMessage("LiveEventBus observe")
    }
}