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

        addManifestPlaceholders(mutableMapOf("APP_NAME" to "My Application")) // 配置主包的应用名称
    }
}

dependencies {
    implementation(libs.google.material)
}