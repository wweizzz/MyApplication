plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.core.imageloader"
    resourcePrefix("imageloader_")
}

dependencies {
    implementation(libs.glide)
    kapt(libs.glide.compiler)
}