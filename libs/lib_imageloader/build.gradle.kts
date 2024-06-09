plugins {
    alias(libs.plugins.nowinandroid.android.library)
}

android {
    namespace = "com.example.william.my.core.imageloader"
}

dependencies {
    implementation(libs.glide)
    kapt(libs.glide.compiler)
}