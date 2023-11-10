plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.module.demo"
    resourcePrefix("demo_")
}

dependencies {
    implementation(libs.google.material)
    implementation(libs.google.flexBox)
    implementation(libs.androidx.viewPager2)
    implementation(project(":libs:lib_widget"))
}