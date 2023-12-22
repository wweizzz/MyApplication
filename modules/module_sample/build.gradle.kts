plugins {
    id("nowinandroid.android.feature")
    id("nowinandroid.android.hilt")
    id("com.google.protobuf")
    //id("nowinandroid.android.room")
    //id("nowinandroid.android.protobuf")
}

android {
    namespace = "com.example.william.my.module.sample"
    //resourcePrefix("sample_")
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

//https://github.com/google/protobuf-gradle-plugin
protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = "com.google.protobuf:protoc:3.24.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.room.paging)
    kapt(libs.androidx.room.compiler)

    //paging
    implementation(libs.autodispose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.rxjava3)

    //DataStore
    implementation(libs.androidx.datastore.runtime)
    implementation(libs.androidx.datastore.preferences.runtime)
    // You need to depend on the lite runtime library, not protobuf-java
    implementation(libs.google.protobuf.javalite)

    //WorkManager
    implementation(libs.androidx.work.ktx)
}
