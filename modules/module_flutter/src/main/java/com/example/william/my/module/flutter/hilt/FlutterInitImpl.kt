package com.example.william.my.module.flutter.hilt

import android.app.Application
import com.example.william.my.lib.hilt.interfaces.IAppInit
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import javax.inject.Inject

class FlutterInitImpl @Inject constructor() : IAppInit {

    private lateinit var mApp: Application

    override fun init(app: Application) {
        this.mApp = app

        initFlutterEngine(app)
    }

    override fun getApp(): Application {
        return mApp
    }

    private fun initFlutterEngine(app: Application) {
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