plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
    alias(libs.plugins.nowinandroid.android.greendao)
    alias(libs.plugins.nowinandroid.android.objectbox)
}

android {
    namespace = "com.example.william.my.module.database"
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))
}

//https://github.com/greenrobot/greenDAO/issues/1110
//tasks.matching {
//    it.name.matches(Regex("\\w*compile\\w*Kotlin"))
//}.configureEach {
//    dependsOn("greendao")
//}
//tasks.configureEach { task ->
//    if (task.name.matches("\\w*compile\\w*Kotlin")) {
//        task.dependsOn('greendao')
//    }
//}