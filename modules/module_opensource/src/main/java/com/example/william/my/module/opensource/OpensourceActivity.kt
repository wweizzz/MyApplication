package com.example.william.my.module.opensource

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath

@Route(path = ARouterPath.Opensource.Main)
class OpensourceActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("Badge", ARouterPath.Opensource.Badge))
        routerItems.add(RouterItem("Banner", ARouterPath.Opensource.Banner))
        routerItems.add(RouterItem("CityPicker", ARouterPath.Opensource.CityPicker))
        routerItems.add(RouterItem("CountdownView", ARouterPath.Opensource.CountdownView))
        routerItems.add(RouterItem("EasyFloat", ARouterPath.Opensource.EasyFloat))
        routerItems.add(RouterItem("FlycoTabLayout", ARouterPath.Opensource.FlycoTabLayout))
        routerItems.add(RouterItem("Lottie", ARouterPath.Opensource.Lottie))
        routerItems.add(RouterItem("PhotoView", ARouterPath.Opensource.PhotoView))
        routerItems.add(RouterItem("PickerView", ARouterPath.Opensource.PickerView))
        routerItems.add(RouterItem("PopWindow", ARouterPath.Opensource.PopWindow))
        routerItems.add(RouterItem("ShadowLayout", ARouterPath.Opensource.ShadowLayout))
        routerItems.add(RouterItem("SVGAPlayer", ARouterPath.Opensource.SVGAPlayer))
        routerItems.add(RouterItem("SwipeLayout", ARouterPath.Opensource.SwipeLayout))
        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("MMKV", ARouterPath.Opensource.MMKV))
        routerItems.add(RouterItem("PermissionX", ARouterPath.Opensource.PermissionX))
        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("OAID", ARouterPath.Opensource.OAID))
        return routerItems
    }
}
