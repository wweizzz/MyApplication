plugins {
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.basic.basic_module"
}

dependencies {
    api(project(":basic:basic_lib"))
}