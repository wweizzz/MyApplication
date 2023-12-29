plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
}

android {
    namespace = "com.example.william.my.basic.basic_module"
}

dependencies {
    implementation(project(":basic:basic_lib"))

    api(project(":libs:lib_okhttp"))
    api(project(":libs:lib_retrofit"))
}