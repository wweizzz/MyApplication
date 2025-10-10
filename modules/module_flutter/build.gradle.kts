plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
    alias(libs.plugins.nowinandroid.android.hilt)
}

android {
    namespace = "com.example.william.my.module.flutter"
    compileOptions {
        //coreLibraryDesugaringEnabled true
    }
    resourcePrefix("flutter_")
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))

    implementation(project(":flutter"))
    //coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")
}