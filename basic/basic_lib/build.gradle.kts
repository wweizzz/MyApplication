plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
    alias(libs.plugins.nowinandroid.android.hilt)
}

android {
    namespace = "com.example.william.my.lib"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.google.material)
    //Utils
    implementation(libs.utils)
    //ImmersionBar
    implementation(libs.immersionbar)
    //LifecycleProvider
    implementation(libs.rxlifecycle)
}