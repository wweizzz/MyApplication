plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.module.arch"
}

dependencies {
    implementation(libs.mavericks)
}