plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
    alias(libs.plugins.nowinandroid.android.greendao)
    alias(libs.plugins.nowinandroid.android.objectbox)
    alias(libs.plugins.nowinandroid.android.hilt)
}

android {
    namespace = "com.example.william.my.module.database"
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))
}

//https://github.com/greenrobot/greenDAO/issues/1110
//Kotlin
tasks.configureEach {
    if (name.matches(Regex("\\w*compile\\w*Kotlin"))) {
        dependsOn("greendao")
    }
    if (name.matches(Regex("\\w*kaptGenerateStubs\\w*Kotlin"))) {
        dependsOn("greendao")
    }
    if (name.matches(Regex("\\w*kapt\\w*Kotlin"))) {
        dependsOn("greendao")
    }
}
//Groovy
//tasks.configureEach { task ->
//    if (task.name.matches("\\w*compile\\w*Kotlin")) {
//        task.dependsOn('greendao')
//    }
//    if (task.name.matches("\\w*kaptGenerateStubs\\w*Kotlin")) {
//        task.dependsOn('greendao')
//    }
//    if (task.name.matches("\\w*kapt\\w*Kotlin")) {
//        task.dependsOn('greendao')
//    }
//}