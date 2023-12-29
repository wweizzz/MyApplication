//https://developer.android.google.cn/jetpack/androidx/releases/room
plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.room)
}

android {
    namespace = "com.example.william.my.module.room"
    //defaultConfig {
    //    javaCompileOptions {
    //        annotationProcessorOptions {
    //            arguments += mapOf(
    //                "room.schemaLocation" to "$projectDir/schemas",
    //                "room.incremental" to "true"
    //            )
    //        }
    //    }
    //}
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))

    //Room
    //implementation(libs.androidx.room.runtime)
    //implementation(libs.androidx.room.ktx)
    //implementation(libs.androidx.room.rxjava3)
    //implementation(libs.androidx.room.paging)
    //kapt(libs.androidx.room.compiler)

    //paging
    implementation(libs.autodispose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.rxjava3)
}
