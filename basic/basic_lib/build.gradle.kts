plugins {
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.lib"
}

dependencies {
    implementation(libs.rxlifecycle)
}