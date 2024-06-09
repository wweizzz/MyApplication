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

    implementation(libs.banner)
    implementation(libs.citypicker)
    implementation(libs.countdownview)
    implementation(libs.easyfloat)
    implementation(libs.tablayout)
    implementation(libs.photoview)
    implementation(libs.pickerview)
    implementation(libs.pictureselector)
    implementation(libs.popwindow)
    implementation(libs.shadowlayout)
    implementation(libs.swipelayout)

    implementation(libs.pag)
    implementation(libs.lottie)
    implementation(libs.svgaPlayer)

    implementation(libs.mmkv)
    implementation(libs.permission)
    //implementation(libs.loadsir)

    implementation(libs.glide)//pictureselector
}