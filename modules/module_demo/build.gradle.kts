plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
}

android {
    namespace = "com.example.william.my.module.demo"
    buildFeatures {
        aidl = true
    }
    resourcePrefix("demo_")
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))

    implementation(libs.pag)
    implementation(libs.google.flexBox)
    implementation(libs.google.material)

    implementation(libs.utils)
}