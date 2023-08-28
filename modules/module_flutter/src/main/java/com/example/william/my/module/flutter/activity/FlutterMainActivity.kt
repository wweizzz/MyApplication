package com.example.william.my.module.flutter.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.library.activity.BaseActivity
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostRouteOptions
import io.flutter.embedding.android.FlutterActivity


@Route(path = ARouterPath.Flutter.Main)
class FlutterMainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openActivity()
        //startWithDefaultIntent()

        finish()
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

    private fun openActivity() {
        val options = FlutterBoostRouteOptions.Builder()
            .pageName("mainPage")
            .arguments(HashMap())
            .requestCode(1000)
            .build()
        FlutterBoost.instance().open(options)
    }
}