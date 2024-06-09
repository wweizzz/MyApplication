package com.example.william.my.module.sample

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Sample.Main)
class SampleActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()

        routerItems.add(RouterItem("DataStore", RouterPath.Sample.DataStore))
        routerItems.add(RouterItem("WorkManager", RouterPath.Sample.WorkManager))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("Coroutines", RouterPath.Sample.Coroutines))
        routerItems.add(RouterItem("Flow", RouterPath.Sample.Flow))

        routerItems.add(
            RouterItem(" ", "")
        )
        routerItems.add(
            RouterItem(
                "ActivityResult",
                RouterPath.Sample.ActivityResultContract
            )
        )
        routerItems.add(
            RouterItem(
                "OnBackPressedDispatcher",
                RouterPath.Sample.OnBackPressedDispatcher
            )
        )
        return routerItems
    }
}