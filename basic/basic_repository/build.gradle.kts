plugins {
    id("nowinandroid.android.basic")
    id("nowinandroid.android.room")
}

android {
    namespace = "com.example.william.my.basic.basic_repository"
}

dependencies {
    api(project(":basic:basic_lib"))
    api(project(":libs:lib_okhttp"))
    api(project(":libs:lib_retrofit"))
}