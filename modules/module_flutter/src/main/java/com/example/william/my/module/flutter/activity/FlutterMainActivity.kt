package com.example.william.my.module.flutter.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostRouteOptions
import io.flutter.embedding.android.FlutterActivity

@Route(path = ARouterPath.Flutter.Main)
class FlutterMainActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "MyCounter",
            "MyConstraints",
            "MyRowColumn",
            "MyFlex",
            "MyWrapFlow",
            "MyStack",
            "MyAlign",
            "MyLayoutBuilder",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        open(string)
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

    private fun open(pageName: String) {
        val options = FlutterBoostRouteOptions.Builder()
            .pageName(pageName)
            .arguments(HashMap())
            .requestCode(1000)
            .build()
        FlutterBoost.instance().open(options)
    }
}