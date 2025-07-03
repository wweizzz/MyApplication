plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.retrofit"
}

dependencies {
    api(project(":libs:lib_okhttp"))
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.retrofit.converter.scalars)
    api(libs.retrofit.adapter.rxjava3)
    implementation(libs.rxlifecycle)
}