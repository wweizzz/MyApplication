plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
    alias(libs.plugins.nowinandroid.android.room)
}

android {
    namespace = "com.example.william.my.module.room"
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))

    implementation(project(":basic:basic_repo"))

    //Room
    //implementation(libs.androidx.room.runtime)
    //implementation(libs.androidx.room.ktx)
    //implementation(libs.androidx.room.rxjava3)
    //implementation(libs.androidx.room.paging)
    //kapt(libs.androidx.room.compiler)

    //paging
    implementation(libs.autodispose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.rxjava3)
}
