package com.example.william.my.module.demo

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Demo.Main)
class DemoActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("Appbar", RouterPath.Demo.Appbar))
        routerItems.add(RouterItem("Dialog", RouterPath.Demo.Dialog))
        routerItems.add(RouterItem("FlexBox", RouterPath.Demo.FlexBox))
        routerItems.add(RouterItem("Fragment1", RouterPath.Demo.Fragment1))
        routerItems.add(RouterItem("Fragment2", RouterPath.Demo.Fragment2))
        routerItems.add(RouterItem("FragmentTabHost", RouterPath.Demo.FragmentTabHost))
        routerItems.add(RouterItem("RecyclerView", RouterPath.Demo.RecyclerView))
        routerItems.add(RouterItem("ViewFlipper", RouterPath.Demo.ViewFlipper))
        routerItems.add(RouterItem("ViewPager", RouterPath.Demo.ViewPager))
        routerItems.add(RouterItem("ViewPager2", RouterPath.Demo.ViewPager2))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("Animator", RouterPath.Demo.Animator))
        routerItems.add(RouterItem("Notification", RouterPath.Demo.Notification))
        routerItems.add(RouterItem("Permission", RouterPath.Demo.Permission))
        routerItems.add(RouterItem("Transition", RouterPath.Demo.TransitionFirst))
        routerItems.add(RouterItem("Typeface", RouterPath.Demo.Typeface))
        routerItems.add(RouterItem("RenderEffect", RouterPath.Demo.RenderEffect))
        routerItems.add(RouterItem("RenderScript", RouterPath.Demo.RenderScript))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("Crop", RouterPath.Demo.Crop))
        routerItems.add(RouterItem("FloatWindow", RouterPath.Demo.FloatWindow))
        routerItems.add(RouterItem("Hook", RouterPath.Demo.Hook))
        routerItems.add(RouterItem("Turntable", RouterPath.Demo.Turntable))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("AsyncTask", RouterPath.Demo.AsyncTask))
        routerItems.add(RouterItem("Broadcast", RouterPath.Demo.Broadcast))
        routerItems.add(RouterItem("HandlerThread", RouterPath.Demo.HandlerThread))
        routerItems.add(RouterItem("JobScheduler", RouterPath.Demo.JobScheduler))
        routerItems.add(RouterItem("Messenger", RouterPath.Demo.Messenger))

        routerItems.add(RouterItem("Service", RouterPath.Demo.Service))
        return routerItems
    }
}