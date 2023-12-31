package com.example.william.my.module.libraries

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Libraries.Main)
class LibrariesActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems = ArrayList<RouterItem>()
        routerItems.add(RouterItem("EventBus", RouterPath.Libraries.EventBus))
        routerItems.add(RouterItem("NinePatch", RouterPath.Libraries.NinePatch))
        return routerItems
    }
}