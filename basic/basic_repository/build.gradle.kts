plugins {
    id("nowinandroid.android.library")
    //id("nowinandroid.android.room")
}

android {
    namespace = "com.example.william.my.basic.basic_repository"
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }
    }
}

dependencies {
    api(project(":basic:basic_library"))
    api(project(":libs:lib_okhttp"))
    api(project(":libs:lib_retrofit"))
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)
}