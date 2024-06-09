package com.example.william.my.module.database

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Database.Main)
class DatabaseActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("GreenDao", RouterPath.Database.GreenDao))
        routerItems.add(RouterItem("ObjectBox", RouterPath.Database.ObjectBox))
        return routerItems
    }
}