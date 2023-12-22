plugins {
    id("nowinandroid.android.feature")
    id("nowinandroid.android.greendao")
    id("nowinandroid.android.objectbox")
}

android {
    namespace = "com.example.william.my.module.opensource"
}

dependencies {
    implementation(libs.badge)
    implementation(libs.banner)
    implementation(libs.citypicker)
    implementation(libs.countdownview)
    implementation(libs.easyfloat)
    implementation(libs.tablayout)
    implementation(libs.lottie)
    implementation(libs.photoview)
    implementation(libs.pickerview)
    implementation(libs.popwindow)
    implementation(libs.shadowlayout)
    implementation(libs.svgaPlayer)
    implementation(libs.swipelayout)


    implementation(libs.mmkv)
    implementation(libs.permission)
    implementation(libs.loadsir)
    implementation(libs.greendao)
    implementation(libs.objectbox)

    implementation(files("lib/oaid_sdk_2.1.0.aar"))
}

// https://github.com/greenrobot/greenDAO/issues/1110
//tasks.matching {
//    it.name.matches(Regex("compile\\w*Kotlin"))
//}.configureEach {
//    dependsOn("greendao")
//}
//tasks.configureEach { task ->
//    if (task.name.matches("compile\\w*Kotlin")) {
//        task.dependsOn('greendao')
//    }
//}