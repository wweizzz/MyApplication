package com.example.william.my.module.arch

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath

@Route(path = ARouterPath.Arch.Main)
class ArchActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("MVP", ARouterPath.Arch.MVP))
        routerItems.add(RouterItem("MVVM", ARouterPath.Arch.MVVM))
        routerItems.add(RouterItem("MVI", ARouterPath.Arch.MVI))
        routerItems.add(RouterItem("Counter", ARouterPath.Arch.Counter))
        routerItems.add(RouterItem("Mavericks", ARouterPath.Arch.Mavericks))
        return routerItems
    }
}