package com.example.william.my.module.flutter.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostRouteOptions

/**
 * 布局类组件用于控制子组件在父容器中的排列和位置，并且决定它们的大小.
 */
@Route(path = ARouterPath.Flutter.Layout)
class FlutterLayoutActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "MyRow", // 水平排列子组件
            "MyColumn", // 垂直排列子组件
            "MyFlex", // 弹性布局，用于按比例分配空间
            "MyWrap", // 流式布局，根据子组件大小自动换行的布局
            "MyFlow", // 流式布局，根据子组件大小自动换行的布局

            "MyStack", // 堆叠布局，可以自由定位子组件
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