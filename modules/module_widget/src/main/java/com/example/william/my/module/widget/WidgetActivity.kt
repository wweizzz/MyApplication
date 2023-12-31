package com.example.william.my.module.widget

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Widget.Main)
class WidgetActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("AlertDialog", RouterPath.Widget.AlertDialog))
        routerItems.add(RouterItem("BlurView", RouterPath.Widget.BlurView))
        routerItems.add(RouterItem("InfiniteImage", RouterPath.Widget.InfiniteImage))
        routerItems.add(RouterItem("MarqueeView", RouterPath.Widget.MarqueeView))
        routerItems.add(RouterItem("Sensor3DView", RouterPath.Widget.Sensor3DView))
        routerItems.add(RouterItem("Spinner", RouterPath.Widget.Spinner))
        routerItems.add(RouterItem("TitleBar", RouterPath.Widget.TitleBar))
        routerItems.add(RouterItem("VerifyCode", RouterPath.Widget.VerifyCode))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("FragmentViewPager", RouterPath.Widget.FragmentViewPager))
        routerItems.add(RouterItem("RecyclerViewNested", RouterPath.Widget.RecyclerViewNested))
        return routerItems
    }
}