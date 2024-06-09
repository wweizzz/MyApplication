package com.example.william.my.module.arch

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Arch.Main)
class ArchActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("MVP", RouterPath.Arch.MVP))
        routerItems.add(RouterItem("MVVM", RouterPath.Arch.MVVM))
        routerItems.add(RouterItem("MVI", RouterPath.Arch.MVI))
        routerItems.add(RouterItem("Counter", RouterPath.Arch.Counter))
        routerItems.add(RouterItem("Mavericks", RouterPath.Arch.Mavericks))
        return routerItems
    }
}