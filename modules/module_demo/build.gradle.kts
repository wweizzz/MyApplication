plugins {
    id("nowinandroid.android.feature")
}

android {
    namespace = "com.example.william.my.module.demo"
    resourcePrefix("demo_")
}

dependencies {
    implementation(libs.google.flexBox)
    implementation(libs.google.material)
    implementation(project(":libs:lib_widget"))
}