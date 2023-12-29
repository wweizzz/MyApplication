plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
}

android {
    namespace = "com.example.william.my.module.opensource"
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))

    implementation(libs.badge)
    implementation(libs.banner)
    implementation(libs.citypicker)
    implementation(libs.countdownview)
    implementation(libs.easyfloat)
    implementation(libs.tablayout)
    implementation(libs.lottie)
    implementation(libs.photoview)

    implementation(libs.google.gson)
    implementation(libs.pickerview)

    implementation(libs.popwindow)
    implementation(libs.shadowlayout)
    implementation(libs.svgaPlayer)
    implementation(libs.swipelayout)

    implementation(libs.mmkv)
    implementation(libs.permission)
    //implementation(libs.loadsir)

    implementation(files("lib/oaid_sdk_2.1.0.aar"))
}