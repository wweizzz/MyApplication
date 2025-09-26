plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
    alias(libs.plugins.nowinandroid.android.hilt)
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
    //implementation(libs.popwindow)
    implementation(libs.shadowlayout)
    implementation(libs.swipelayout)

    implementation(libs.pag)
    implementation(libs.lottie)
    implementation(libs.svgaPlayer)

    implementation(libs.mmkv)
    implementation(libs.permission)
    implementation(libs.loadsir)

    implementation(libs.rxandroid)

    implementation(libs.blurview)

    //extra
    implementation(libs.glide)//pictureselector

    // Import the BoM for the Firebase platform
    implementation(platform(libs.google.firebase.bom))

    // Add the dependency for the Firebase AI Logic library. When using the BoM,
    // you don't specify versions in Firebase library dependencies
    implementation(libs.google.firebase.ai)

    //implementation(libs.upgrade)
    //implementation(libs.upgrade.ui)
    //implementation("com.github.lzjin:SideBarView:1.0.1")
    //implementation("com.github.qdxxxx:StickyHeaderDecoration:1.0.1")
}