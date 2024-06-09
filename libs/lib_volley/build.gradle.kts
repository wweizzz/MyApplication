plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.volley"
}

dependencies {
    api(libs.google.gson)
    api(libs.volley)
}