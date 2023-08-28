plugins {
    id("nowinandroid.android.library")
}

android {
    namespace = "com.example.william.my.basic.basic_module"
}

dependencies {
    api(project(":basic:basic_library"))
}