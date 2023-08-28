plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.module.utils"
    resourcePrefix("utils_")
}

dependencies {
    implementation(libs.utils)
}