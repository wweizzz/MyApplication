plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
    alias(libs.plugins.nowinandroid.android.protobuf)
}

android {
    namespace = "com.example.william.my.module.sample"
    //resourcePrefix("sample_")
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))

    implementation(project(":basic:basic_data"))

    //DataStore
    implementation(libs.androidx.datastore.runtime)
    implementation(libs.androidx.datastore.preferences.runtime)
    //WorkManager
    implementation(libs.androidx.workmanager.ktx)
}
