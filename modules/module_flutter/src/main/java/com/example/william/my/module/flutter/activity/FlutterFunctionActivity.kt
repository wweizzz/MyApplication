package com.example.william.my.module.flutter.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostRouteOptions

@Route(path = RouterPath.Flutter.Function)
class FlutterFunctionActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "MyWillPopScope", // 导航返回拦截
            "MyInheritedWidget", // 数据共享组件
            "MyValueListenableBuilder", // 数据源监听
            "MyFutureBuilder", // 异步UI更新
            "MyStreamBuilder", // 异步UI更新
            "MyDialog", // Dialog
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