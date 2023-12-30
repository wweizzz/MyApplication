package com.example.william.my.module.sample

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath

@Route(path = ARouterPath.Sample.Main)
class SampleActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()

        routerItems.add(RouterItem("DataStore", ARouterPath.Sample.DataStore))
        routerItems.add(RouterItem("WorkManager", ARouterPath.Sample.WorkManager))

        routerItems.add(RouterItem(" ", ""))
        routerItems.add(RouterItem("Coroutines", ARouterPath.Sample.Coroutines))
        routerItems.add(RouterItem("Flow", ARouterPath.Sample.Flow))

        routerItems.add(
            RouterItem(" ", "")
        )
        routerItems.add(
            RouterItem(
                "ActivityResult",
                ARouterPath.Sample.ActivityResultContract
            )
        )
        routerItems.add(
            RouterItem(
                "OnBackPressedDispatcher",
                ARouterPath.Sample.OnBackPressedDispatcher
            )
        )
        return routerItems
    }
}