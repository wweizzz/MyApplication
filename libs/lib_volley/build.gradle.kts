plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.volley"
}

dependencies {
    api(libs.google.gson)
    //volley
    api(libs.volley)
    //okhttp
    api(libs.okhttp)
    api(libs.okhttp.logging)
}