plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
    alias(libs.plugins.nowinandroid.android.room)
}

android {
    namespace = "com.example.william.my.basic.basic_repository"
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))

    api(project(":basic:basic_data"))

    api(project(":libs:lib_okhttp"))
    api(project(":libs:lib_retrofit"))
}