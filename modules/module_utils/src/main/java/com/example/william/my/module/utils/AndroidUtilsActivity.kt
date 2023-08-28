package com.example.william.my.module.utils

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath

/**
 * https://github.com/Blankj/AndroidUtilCode
 */
@Route(path = ARouterPath.Utils.Main)
class AndroidUtilsActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("FileIOUtilsActivity", ARouterPath.Utils.FileIOUtils))
        routerItems.add(RouterItem("PermissionUtilsActivity", ARouterPath.Utils.PermissionUtils))
        return routerItems
    }
}