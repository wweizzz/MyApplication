package com.example.william.my.module.libraries.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.core.eventbus.flow.FlowEventBus
import com.example.william.my.core.eventbus.livedata.LiveEventBus
import com.example.william.my.core.eventbus.rxjava.RxEventBus
import com.example.william.my.lib.eventbus.EventBusHelper
import com.example.william.my.module.libraries.event.GlobalEvent
import com.example.william.my.module.libraries.event.StickyEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@Route(path = ARouterPath.Libraries.EventBus)
class EventBusActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "register",
            "postEventBus",
            "postStickyEventBus",

            "RxEventBus",
            "postRxEventBus",
            "postStickyRxEventBus",

            "observeLiveEvent",
            "postLiveEvent",
            "postStickyLiveEvent",

            "observeFlowEvent",
            "postFlowEvent",
            "postStickyFlowEvent",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                if (!EventBusHelper.isRegistered(this@EventBusActivity)) {
                    EventBusHelper.register(this@EventBusActivity)
                } else {
                    EventBusHelper.unregister(this@EventBusActivity)
                }
            }

            1 -> {
                EventBusHelper.postEvent(GlobalEvent("send GlobalEvent by Activity"))
            }

            2 -> {
                EventBusHelper.postStickyEvent(StickyEvent("send StickyEvent by Activity"))
            }

            3 -> {
                observeRxBusEvent()
            }

            4 -> {
                RxEventBus.postEvent(GlobalEvent("send GlobalEvent by Activity"))
            }

            5 -> {
                RxEventBus.postStickyEvent(StickyEvent("send StickyEvent by Activity"))
            }

            6 -> {
                observeLiveEventBus()
            }

            7 -> {
                LiveEventBus.postEvent(this, GlobalEvent("send GlobalEvent by Activity"))
            }

            8 -> {
                LiveEventBus.postEvent(this, StickyEvent("send StickyEvent by Activity"))
            }


            9 -> {
                observeFlowEventBus()
            }

            10 -> {
                FlowEventBus.postEvent(GlobalEvent("send GlobalEvent by Activity"))
            }

            11 -> {
                FlowEventBus.postEvent(StickyEvent("send StickyEvent by Activity"))
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGlobalEvent(event: GlobalEvent) {
        showMessage(event.message)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onStickyEvent(event: StickyEvent) {
        showMessage(event.message)
    }

    private fun observeRxBusEvent() {
        val globalDisposable = RxEventBus
            .observeEvent(GlobalEvent::class.java)
            .subscribe {
                showMessage(it.message)
            }

        val stickyDisposable = RxEventBus
            .observeEvent(StickyEvent::class.java)
            .subscribe {
                showMessage(it.message)
            }
    }

    private fun observeLiveEventBus() {
        LiveEventBus.observeEvent<GlobalEvent>(this) {
            showMessage(it.message)
        }
        LiveEventBus.observeEvent<StickyEvent>(this, isSticky = true) {
            showMessage(it.message)
        }
    }

    private fun observeFlowEventBus() {
        FlowEventBus.observeEvent<GlobalEvent>(this) {
            showMessage(it.message)
        }
        FlowEventBus.observeEvent<StickyEvent>(this, isSticky = true) {
            showMessage(it.message)
        }
    }
}