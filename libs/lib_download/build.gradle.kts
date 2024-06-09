plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.download"
}

dependencies {
    api(project(":libs:lib_okhttp"))
    api(project(":libs:lib_retrofit"))
}