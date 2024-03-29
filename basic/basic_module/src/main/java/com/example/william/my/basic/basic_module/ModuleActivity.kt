package com.example.william.my.basic.basic_module

import android.os.Bundle
import android.os.Looper
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.utils.Utils

/**
 * Plugins:
 * GsonForMartPlus
 * Google library Version Querier
 * DeteKt
 * Alibaba Java Coding Guidelines
 * <p>
 * TODO：basic_room:module_room(room,paging)
 *       basic_repository:module_arch
 *       ViewModel hilt
 *       FlowEventBus
 */
@Route(path = RouterPath.Module_Main)
class ModuleActivity : RouterRecyclerActivity() {

    @JvmField
    @Autowired(name = "param_key")
    var paramKey: String? = null

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("FlutterActivity", RouterPath.Flutter.Main))

        routerItems.add(RouterItem("", ""))
        routerItems.add(RouterItem("OpensourceActivity", RouterPath.Opensource.Main))
        routerItems.add(RouterItem("DatabaseActivity", RouterPath.Database.Main))
        routerItems.add(RouterItem("UtilsActivity", RouterPath.Utils.Main))

        routerItems.add(RouterItem("", ""))
        routerItems.add(RouterItem("DemoActivity", RouterPath.Demo.Main))
        routerItems.add(RouterItem("WidgetActivity", RouterPath.Widget.Main))
        routerItems.add(RouterItem("LibrariesActivity", RouterPath.Libraries.Main))

        routerItems.add(RouterItem("", ""))
        routerItems.add(RouterItem("NetworkActivity", RouterPath.Network.Main))
        routerItems.add(RouterItem("SampleActivity", RouterPath.Sample.Main))

        routerItems.add(RouterItem("", ""))
        routerItems.add(RouterItem("ArchActivity", RouterPath.Arch.Main))
        routerItems.add(RouterItem("RoomActivity", RouterPath.Room.Main))

        return routerItems
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Looper.myQueue().addIdleHandler {
            Utils.d(tag, "addIdleHandler: queueIdle " + Thread.currentThread().name)
            false
        }
    }
}