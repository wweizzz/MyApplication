package com.example.william.my.module.widget

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath

@Route(path = ARouterPath.Widget.Main)
class WidgetActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("AlertDialog", ARouterPath.Widget.AlertDialog))
        routerItems.add(RouterItem("BlurView", ARouterPath.Widget.BlurView))
        routerItems.add(RouterItem("InfiniteImage", ARouterPath.Widget.InfiniteImage))
        routerItems.add(RouterItem("MarqueeView", ARouterPath.Widget.MarqueeView))
        routerItems.add(RouterItem("Sensor3DView", ARouterPath.Widget.Sensor3DView))
        routerItems.add(RouterItem("Spinner", ARouterPath.Widget.Spinner))
        routerItems.add(RouterItem("TitleBar", ARouterPath.Widget.TitleBar))
        routerItems.add(RouterItem("VerifyCode", ARouterPath.Widget.VerifyCode))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("FragmentViewPager", ARouterPath.Widget.FragmentViewPager))
        routerItems.add(RouterItem("RecyclerViewNested", ARouterPath.Widget.RecyclerViewNested))
        return routerItems
    }
}