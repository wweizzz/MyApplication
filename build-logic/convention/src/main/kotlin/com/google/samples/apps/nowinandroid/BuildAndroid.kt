package com.google.samples.apps.nowinandroid

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension

/**
 * Configure base build with Android options
 */
fun configureApplicationAndroid(
    commonExtension: ApplicationExtension,
) {
    commonExtension.apply {
        compileSdk = 34
        defaultConfig {
            minSdk = 21
            targetSdk = 34
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            multiDexEnabled = true // dex分包支持
        }
        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            debug {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
//        buildFeatures {
//            // Determines whether to generate a BuildConfig class.
//            buildConfig = true
//            // Determines whether to support View Binding.
//            // Note that the viewBinding.enabled property is now deprecated.
//            viewBinding = true
//            // Determines whether to support Data Binding.
//            // Note that the dataBinding.enabled property is now deprecated.
//            dataBinding = false
//            // Determines whether to generate binder classes for your AIDL files.
//            aidl = true
//            // Determines whether to support RenderScript.
//            renderScript = true
//            // Determines whether to support injecting custom variables into the module’s R class.
//            resValues = true
//            // Determines whether to support shader AOT compilation.
//            shaders = true
//        }
    }
}

/**
 * Configure base build with Android options
 */
fun configureLibraryAndroid(
    commonExtension: LibraryExtension,
) {
    commonExtension.apply {
        compileSdk = 34
        defaultConfig {
            minSdk = 21
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
            multiDexEnabled = true // dex分包支持
        }
        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            debug {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
//        buildFeatures {
//            // Determines whether to generate a BuildConfig class.
//            buildConfig = true
//            // Determines whether to support View Binding.
//            // Note that the viewBinding.enabled property is now deprecated.
//            viewBinding = true
//            // Determines whether to support Data Binding.
//            // Note that the dataBinding.enabled property is now deprecated.
//            dataBinding = false
//            // Determines whether to generate binder classes for your AIDL files.
//            aidl = true
//            // Determines whether to support RenderScript.
//            renderScript = true
//            // Determines whether to support injecting custom variables into the module’s R class.
//            resValues = true
//            // Determines whether to support shader AOT compilation.
//            shaders = true
//        }
    }
}
