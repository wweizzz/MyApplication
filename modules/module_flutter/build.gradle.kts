plugins {
    id("nowinandroid.android.feature")
}

android {
    namespace = "com.example.william.my.module.flutter"
    resourcePrefix("flutter_")
}

dependencies {
    implementation(project(":flutter"))
    implementation(project(":flutter_boost"))
}