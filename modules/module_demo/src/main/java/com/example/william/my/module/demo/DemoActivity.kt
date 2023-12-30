package com.example.william.my.module.demo

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath

@Route(path = ARouterPath.Demo.Main)
class DemoActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("Appbar", ARouterPath.Demo.Appbar))
        routerItems.add(RouterItem("Dialog", ARouterPath.Demo.Dialog))
        routerItems.add(RouterItem("FlexBox", ARouterPath.Demo.FlexBox))
        routerItems.add(RouterItem("Fragment1", ARouterPath.Demo.Fragment1))
        routerItems.add(RouterItem("Fragment2", ARouterPath.Demo.Fragment2))
        routerItems.add(RouterItem("FragmentTabHost", ARouterPath.Demo.FragmentTabHost))
        routerItems.add(RouterItem("RecyclerView", ARouterPath.Demo.RecyclerView))
        routerItems.add(RouterItem("ViewFlipper", ARouterPath.Demo.ViewFlipper))
        routerItems.add(RouterItem("ViewPager", ARouterPath.Demo.ViewPager))
        routerItems.add(RouterItem("ViewPager2", ARouterPath.Demo.ViewPager2))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("Animator", ARouterPath.Demo.Animator))
        routerItems.add(RouterItem("Notification", ARouterPath.Demo.Notification))
        routerItems.add(RouterItem("Permission", ARouterPath.Demo.Permission))
        routerItems.add(RouterItem("Transition", ARouterPath.Demo.Transition))
        routerItems.add(RouterItem("Typeface", ARouterPath.Demo.Typeface))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("Crop", ARouterPath.Demo.Crop))
        routerItems.add(RouterItem("FloatWindow", ARouterPath.Demo.FloatWindow))
        routerItems.add(RouterItem("Hook", ARouterPath.Demo.Hook))
        routerItems.add(RouterItem("Turntable", ARouterPath.Demo.Turntable))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("AsyncTask", ARouterPath.Demo.AsyncTask))
        routerItems.add(RouterItem("Broadcast", ARouterPath.Demo.Broadcast))
        routerItems.add(RouterItem("HandlerThread", ARouterPath.Demo.HandlerThread))
        routerItems.add(RouterItem("JobScheduler", ARouterPath.Demo.JobScheduler))
        routerItems.add(RouterItem("Messenger", ARouterPath.Demo.Messenger))

        routerItems.add(RouterItem("Service", ARouterPath.Demo.Service))
        return routerItems
    }
}