plugins {
    id("nowinandroid.android.feature")
}

android {
    namespace = "com.example.william.my.module.utils"
    resourcePrefix("utils_")
}

dependencies {
    implementation(libs.utils)
}