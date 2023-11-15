package com.example.william.my.module.flutter

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import io.flutter.embedding.android.FlutterActivity

@Route(path = ARouterPath.Flutter.Main)
class FlutterMainActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(RouterItem("布局类组件", ARouterPath.Flutter.Layout))
        routerItems.add(RouterItem("容器类组件", ARouterPath.Flutter.Container))
        routerItems.add(RouterItem("滚动类组件", ARouterPath.Flutter.Scroll))
        routerItems.add(RouterItem("功能型组件", ARouterPath.Flutter.Function))
        routerItems.add(RouterItem("网络请求", ARouterPath.Flutter.Http))
        routerItems.add(RouterItem("状态管理", ARouterPath.Flutter.State))
        routerItems.add(RouterItem("其他", ARouterPath.Flutter.Other))
        return routerItems
    }

    private fun startWithDefaultIntent() {
        startActivity(
            FlutterActivity
                .createDefaultIntent(this)
        )
    }

    private fun startWithNewEngine() {
        startActivity(
            FlutterActivity
                .withNewEngine()
                .initialRoute("/my_route")
                .build(this)
        )
    }

    private fun startWithCachedEngine() {
        startActivity(
            FlutterActivity
                .withCachedEngine("my_engine_id")
                .build(this)
        )
    }
}