package com.example.william.my.module.flutter.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostRouteOptions

/**
 * 容器类组件用于装饰或包裹子组件，不负责决定子组件的排列和位置，而是用于美化或包裹子组件。
 */
@Route(path = ARouterPath.Flutter.Container)
class FlutterContainerActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "MyPadding",
            "MyDecoratedBox",
            "MyContainer",
            "MyAlign",
            "MyCenter",
            "MyConstrainedBox",
            "MySizedBox",
            "MyLayoutBuilder",
            "MyGestureDetector",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        open(string)
    }

    private fun open(pageName: String) {
        val arguments = mutableMapOf<String, String>()
        arguments["pageName"] = pageName
        val options = FlutterBoostRouteOptions.Builder()
            .pageName(pageName)
            .arguments(arguments as Map<String, Any>)
            .requestCode(1000)
            .build()
        FlutterBoost.instance().open(options)
    }
}