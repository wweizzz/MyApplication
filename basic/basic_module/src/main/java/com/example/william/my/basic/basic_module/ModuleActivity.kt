package com.example.william.my.basic.basic_module

import android.os.Bundle
import android.os.Looper
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.utils.Utils

/**
 * Plugins:
 * GsonForMartPlus
 * Google library Version Querier
 * DeteKt
 * Alibaba Java Coding Guidelines
 */
@Route(path = ARouterPath.Module_Main)
class ModuleActivity : RouterRecyclerActivity() {

    @JvmField
    @Autowired(name = "param_key")
    var paramKey: String? = null

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("FlutterActivity", ARouterPath.Flutter.Main))
        routerItems.add(RouterItem("ArchActivity", ARouterPath.Arch.Main))
        routerItems.add(RouterItem("DemoActivity", ARouterPath.Demo.Main))
        routerItems.add(RouterItem("LibrariesActivity", ARouterPath.Libraries.Main))
        routerItems.add(RouterItem("NetworkActivity", ARouterPath.Network.Main))
        routerItems.add(RouterItem("OpensourceActivity", ARouterPath.Opensource.Main))
        routerItems.add(RouterItem("SampleActivity", ARouterPath.Sample.Main))
        routerItems.add(RouterItem("UtilsActivity", ARouterPath.Utils.Main))
        return routerItems
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Looper.myQueue().addIdleHandler {
            Utils.d("addIdleHandler: queueIdle " + Thread.currentThread().name)
            false
        }
    }
}