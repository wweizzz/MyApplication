plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.module.flutter"
    resourcePrefix("flutter_")
}

dependencies {
    implementation(project(":flutter"))
    implementation(project(":flutter_boost"))
}