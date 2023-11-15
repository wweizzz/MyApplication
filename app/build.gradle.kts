plugins {
    id("nowinandroid.android.application")
    id("nowinandroid.android.hilt")
    id("nowinandroid.android.basic")
    id("nowinandroid.android.feature")
}

android {
    namespace = "com.example.william.my.application"
    defaultConfig {
        applicationId = "com.example.william.my.application"
        versionCode = 100
        versionName = "1.0.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        ndk {
            // armeabi：万金油架构平台（占用率：0%）
            // armeabi-v7a：曾经主流的架构平台（占用率：10%）
            // arm64-v8a：目前主流架构平台（占用率：90%）
            //abiFilters 'armeabi-v7a', 'arm64-v8a' // , 'x86', 'x86_64'
            abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        addManifestPlaceholders(mutableMapOf("APP_NAME" to "My Application")) // 配置主包的应用名称
    }
}

dependencies {
    implementation(libs.google.material)
}