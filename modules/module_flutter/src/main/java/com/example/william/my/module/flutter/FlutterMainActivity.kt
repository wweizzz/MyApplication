package com.example.william.my.module.flutter

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import io.flutter.embedding.android.FlutterActivity

@Route(path = RouterPath.Flutter.Main)
class FlutterMainActivity : BasicResponseActivity() {

    override fun initView() {
        super.initView()

        //startWithNewEngine()
        startWithCachedEngine()

        finish()
    }

    private fun startDefault() {
        startActivity(
            FlutterActivity
                .createDefaultIntent(this)
        )
    }

    private fun startWithNewEngine() {
        startActivity(
            FlutterActivity
                .withNewEngine()
                .initialRoute("/")
                .build(this)
        )
    }

    private fun startWithCachedEngine() {
        startActivity(
            FlutterActivity
                .withCachedEngine("cached_engine_id")
                .build(this)
        )
    }
}