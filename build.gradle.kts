// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        //classpath 'com.android.tools.build:gradle:x.x.x'
        //classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:x.x.x"
        classpath(libs.gradlePlugin.protobuf)
        classpath(libs.gradlePlugin.arouter)
        classpath(libs.gradlePlugin.greendao)
        classpath(libs.gradlePlugin.objectbox)
    }
}

//only buildscript {}, pluginManagement {} and other plugins {} script blocks are allowed before plugins {} blocks, no other statements are allowed.
//plugins {
//    id("com.android.application") version "8.1.0" apply false
//    id("com.android.library") version "8.1.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
//}

// Lists all plugins used throughout the project
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false

    alias(libs.plugins.android.ksp) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.android.room) apply false

    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.compose) apply false
}