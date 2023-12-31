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
            "registerEventBus",
            "postEventBus",
            "postStickyEventBus",

            "",
            "observeRxEventBus",
            "postRxEventBus",
            "postStickyRxEventBus",

            "",
            "observeLiveEvent",
            "postLiveEvent",
            "postStickyLiveEvent",

            "",
            "observeFlowEvent",
            "postFlowEvent",
            "postStickyFlowEvent",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                registerEventBus()
            }

            1 -> {
                EventBusHelper.postEvent(GlobalEvent("send EventBus by Activity"))
            }

            2 -> {
                EventBusHelper.postStickyEvent(StickyEvent("send EventBus Sticky by Activity"))
            }

            4 -> {
                observeRxBusEvent()
            }

            5 -> {
                RxEventBus.postEvent(GlobalEvent("send RxEventBus by Activity"))
            }

            6 -> {
                RxEventBus.postStickyEvent(StickyEvent("send RxEventBus Sticky by Activity"))
            }

            8 -> {
                observeLiveEventBus()
            }

            9 -> {
                LiveEventBus.postEvent(this, GlobalEvent("send LiveEventBus by Activity"))
            }

            10 -> {
                LiveEventBus.postEvent(this, StickyEvent("send LiveEventBus Sticky by Activity"))
            }


            12 -> {
                observeFlowEventBus()
            }

            13 -> {
                FlowEventBus.postEvent(GlobalEvent("send FlowEventBus by Activity"))
            }

            14 -> {
                FlowEventBus.postEvent(StickyEvent("send FlowEventBus Sticky by Activity"))
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

    private fun registerEventBus() {
        if (!EventBusHelper.isRegistered(this@EventBusActivity)) {
            EventBusHelper.register(this@EventBusActivity)
            showMessage("register EventBus")
        } else {
            EventBusHelper.unregister(this@EventBusActivity)
            showMessage("unregister EventBus")
        }
    }

    private fun observeRxBusEvent() {
        val globalDisposable = RxEventBus.observeEvent(GlobalEvent::class.java).subscribe {
                showMessage(it.message)
            }

        val stickyDisposable = RxEventBus.observeEvent(StickyEvent::class.java).subscribe {
                showMessage(it.message)
            }
        showMessage("observe RxBusEvent")
    }

    private fun observeLiveEventBus() {
        LiveEventBus.observeEvent<GlobalEvent>(this) {
            showMessage(it.message)
        }
        LiveEventBus.observeEvent<StickyEvent>(this, isSticky = true) {
            showMessage(it.message)
        }
        showMessage("observe LiveEventBus")
    }

    /**
     * TODO
     */
    private fun observeFlowEventBus() {
        FlowEventBus.observeEvent<GlobalEvent>(this) {
            showMessage(it.message)
        }
        FlowEventBus.observeEvent<StickyEvent>(this, isSticky = true) {
            showMessage(it.message)
        }
        showMessage("observe FlowEventBus")
    }
}