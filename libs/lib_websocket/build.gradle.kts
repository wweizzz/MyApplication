plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.websocket"
}

dependencies {
    api(libs.google.gson)
    api(libs.okhttp)
    api(libs.rxandroid)
}