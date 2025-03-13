package com.example.william.my.module.libraries.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.eventbus.flow.FlowEventBus
import com.example.william.my.module.libraries.event.GlobalEvent
import com.example.william.my.module.libraries.event.StickyEvent

/**
 * 延迟发送    有序接收    Sticky    生命感知    可跨进程    线程分发
 * ✅        ✅        ✅        ✅        ❌        ✅
 */
@Route(path = RouterPath.Libraries.FlowEventBus)
class FlowEventBusActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "observeFlowEventBus",
            "postFlowEventBus",
            "postStickyFlowEventBus",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                observeFlowEventBus()
            }

            1 -> {
                FlowEventBus.postEvent(
                    this,
                    GlobalEvent("FlowEventBus post by Activity")
                )
            }

            2 -> {
                FlowEventBus.postEvent(
                    this,
                    StickyEvent("FlowEventBus postSticky by Activity")
                )
            }
        }
    }

    private fun observeFlowEventBus() {
        FlowEventBus.observeEvent<GlobalEvent>(this) {
            showEventMessage(it.message)
        }
        FlowEventBus.observeEvent<StickyEvent>(this, isSticky = true) {
            showEventMessage(it.message)
        }
        showEventMessage("FlowEventBus observe ")
    }
}