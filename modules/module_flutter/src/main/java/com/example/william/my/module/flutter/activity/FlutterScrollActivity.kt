package com.example.william.my.module.flutter.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostRouteOptions

@Route(path = RouterPath.Flutter.Scroll)
class FlutterScrollActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "MyListView",
            "MyGridView",
            "MyScrollView",
            "",
            "MyPageView",
            "MyTabBarView",
            "",
            "MyPageView",
            "MyTabBarView",
            "",
            "MyAnimatedList",
            "MyCustomScrollView",
            "MyNestedScrollView",
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