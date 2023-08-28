plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.module.libraries"
    resourcePrefix("libs_")
}

dependencies {
    implementation(project(":libs:lib_eventbus"))
    implementation(project(":libs:lib_ninepatch"))
}