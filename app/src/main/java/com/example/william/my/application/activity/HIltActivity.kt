package com.example.william.my.application.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.william.my.application.R
import com.example.william.my.basic.basic_module.utils.Utils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject
import javax.inject.Qualifier

@AndroidEntryPoint // 将依赖项注入 Android 类
class HiltActivity : AppCompatActivity() {

    @Inject
    lateinit var driver: Driver

    @Inject
    lateinit var engine: Engine // 使用 @Binds 注入实例

    @Inject
    lateinit var providesData: ProvidesData // 使用 @Provides 注入实例

    @Inject
    lateinit var exampleService: ExampleServiceImpl // 为同一类型提供多个绑定

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        driver.println()

        engine.println()

        providesData.println()

        exampleService.println()

    }
}

/**
 * 构造函数的依赖注入
 */
class Driver @Inject constructor() {

    fun println() {
        Utils.logcat("Hilt", "@Inject")
    }
}

/**
 * 接口的依赖注入
 */
interface Engine {
    fun println()
}

class EngineImpl @Inject constructor() : Engine {

    override fun println() {
        Utils.logcat("Hilt", "@Binds")
    }
}

@Module // Hilt 模块
@InstallIn(ActivityComponent::class)
abstract class EngineModule {

    /**
     * 使用@Binds注入接口实例
     *
     * 带有注解的函数会向 Hilt 提供以下信息：
     *      函数返回类型会告知 Hilt 该函数提供哪个接口的实例。
     *      函数参数会告知 Hilt 要提供哪种实现。
     */
    @Binds
    abstract fun bindEngine(engineImpl: EngineImpl): Engine
}

/**
 * 第三方类的依赖注入
 */
class ProvidesData {
    fun println() {
        Utils.logcat("Hilt", "@Provides")
    }
}

@Module // Hilt 模块
@InstallIn(ActivityComponent::class)
object ProvidesModule {

    /**
     * 使用 @Provides 注入实例
     *
     * 带有注解的函数会向 Hilt 提供以下信息：
     *      函数返回类型会告知 Hilt 函数提供哪个类型的实例。
     *      函数参数会告知 Hilt 相应类型的依赖项。
     *      函数主体会告知 Hilt 如何提供相应类型的实例。每当需要提供该类型的实例时，Hilt 都会执行函数主体。
     */
    @Provides
    fun provideProvidesData(): ProvidesData {
        return ProvidesData()
    }
}

/**
 * 为同一类型提供多个依赖注入
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Other

/**
 * 为同一类型提供多个绑定
 */
@Module // Hilt 模块
@InstallIn(ActivityComponent::class)
object MultipleModule {

    @Auth
    @Provides
    fun provideAuth(): String {
        return "Auth"
    }

    @Other
    @Provides
    fun provideOther(): String {
        return "Other"
    }
}

class ExampleServiceImpl @Inject constructor(
    @param:Auth private val auth: String, // Auth
    @param:Other private val other: String // Other
) {

    fun println() {
        Utils.logcat("Hilt", "@Qualifier: $auth,$other")
    }
}