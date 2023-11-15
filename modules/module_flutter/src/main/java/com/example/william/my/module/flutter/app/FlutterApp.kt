package com.example.william.my.module.flutter.app

import android.content.Intent
import com.example.william.my.library.app.BaseAppInit
import com.example.william.my.module.flutter.FlutterMainActivity
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostDelegate
import com.idlefish.flutterboost.FlutterBoostRouteOptions
import com.idlefish.flutterboost.containers.FlutterBoostActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class FlutterApp : BaseAppInit() {

    private var flutterEngine: FlutterEngine? = null

    override fun init() {
        super.init()

        initFlutterBoost()

        //initFlutterEngine()
    }

    private fun initFlutterBoost() {
        FlutterBoost.instance().setup(app, object : FlutterBoostDelegate {
            override fun pushNativeRoute(options: FlutterBoostRouteOptions) {
                //这里根据 options.pageName 来判断你想跳转哪个页面
                val intent = Intent(
                    FlutterBoost.instance().currentActivity(),
                    FlutterMainActivity::class.java
                )
                FlutterBoost.instance().currentActivity()
                    .startActivityForResult(intent, options.requestCode())
            }

            override fun pushFlutterRoute(options: FlutterBoostRouteOptions) {
                val intent =
                    FlutterBoostActivity.CachedEngineIntentBuilder(FlutterBoostActivity::class.java)
                        .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                        .destroyEngineWithActivity(false)
                        .uniqueId(options.uniqueId())
                        .url(options.pageName())
                        .urlParams(options.arguments())
                        .build(FlutterBoost.instance().currentActivity())
                FlutterBoost.instance().currentActivity().startActivity(intent)
            }
        }) { engine: FlutterEngine ->

        }
    }

    private fun initFlutterEngine() {
        // 实例化FlutterEngine。
        // Instantiate a FlutterEngine.
        flutterEngine = FlutterEngine(app)

        // 配置初始路由
        // Configure an initial route.
        // flutterEngine.navigationChannel.setInitialRoute("your/route/here");

        // 开始执行Dart代码来预热FlutterEngine
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine?.dartExecutor?.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // 缓存FlutterActivity要使用的FlutterEngine。
        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put("my_engine_id", flutterEngine)
    }
}