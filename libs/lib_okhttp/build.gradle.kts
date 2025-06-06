plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.okhttp"
}

dependencies {
    // SharedPreferences.edit
    implementation(libs.androidx.core.ktx)
    api(libs.google.gson)
    api(libs.okhttp)
    api(libs.okhttp.logging)
}