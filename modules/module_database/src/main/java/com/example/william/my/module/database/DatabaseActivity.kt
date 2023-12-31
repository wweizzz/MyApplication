package com.example.william.my.module.database

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath

@Route(path = ARouterPath.Database.Main)
class DatabaseActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("GreenDao", ARouterPath.Database.GreenDao))
        routerItems.add(RouterItem("ObjectBox", ARouterPath.Database.ObjectBox))
        return routerItems
    }
}