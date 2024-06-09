package com.example.william.my.module.flutter.app

import com.example.william.my.lib.app.BaseAppInit
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class FlutterApp : BaseAppInit() {

    override fun init() {
        super.init()

        initFlutterEngine()
    }

    private fun initFlutterEngine() {
        // 实例化FlutterEngine。
        // Instantiate a FlutterEngine.
        val flutterEngine = FlutterEngine(app)

        // 配置初始路由
        // Configure an initial route.
        flutterEngine.navigationChannel.setInitialRoute("/");

        // 开始执行Dart代码来预热FlutterEngine
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // 缓存FlutterActivity要使用的FlutterEngine。
        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put("cached_engine_id", flutterEngine)
    }
}