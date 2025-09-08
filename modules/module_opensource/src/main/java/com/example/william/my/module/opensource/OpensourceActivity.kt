package com.example.william.my.module.opensource

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Opensource.Main)
class OpensourceActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("Banner", RouterPath.Opensource.Banner))
        routerItems.add(RouterItem("CityPicker", RouterPath.Opensource.CityPicker))
        routerItems.add(RouterItem("CountdownView", RouterPath.Opensource.CountdownView))
        routerItems.add(RouterItem("EasyFloat", RouterPath.Opensource.EasyFloat))
        routerItems.add(RouterItem("FlycoTabLayout", RouterPath.Opensource.FlycoTabLayout))
        routerItems.add(RouterItem("PhotoView", RouterPath.Opensource.PhotoView))
        routerItems.add(RouterItem("PickerView", RouterPath.Opensource.PickerView))
        routerItems.add(RouterItem("PictureSelector", RouterPath.Opensource.PictureSelector))
        routerItems.add(RouterItem("PopWindow", RouterPath.Opensource.PopWindow))
        routerItems.add(RouterItem("ShadowLayout", RouterPath.Opensource.ShadowLayout))
        routerItems.add(RouterItem("SwipeLayout", RouterPath.Opensource.SwipeLayout))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("Pag", RouterPath.Opensource.Pag))
        routerItems.add(RouterItem("Lottie", RouterPath.Opensource.Lottie))
        routerItems.add(RouterItem("SVGAPlayer", RouterPath.Opensource.SVGAPlayer))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("LoadSir", RouterPath.Opensource.LoadSir))
        routerItems.add(RouterItem("MMKV", RouterPath.Opensource.MMKV))
        routerItems.add(RouterItem("PermissionX", RouterPath.Opensource.PermissionX))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("RxJava", RouterPath.Opensource.RxJava))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("Imagen", RouterPath.Opensource.Imagen))
        return routerItems
    }
}
