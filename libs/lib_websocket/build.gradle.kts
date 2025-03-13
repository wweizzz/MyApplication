plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.websocket"
}

dependencies {
    api(libs.google.gson)
    //okhttp
    api(libs.okhttp)
    api(libs.okhttp.logging)
    //rxandroid
    api(libs.rxandroid)
}