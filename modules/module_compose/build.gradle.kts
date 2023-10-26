plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.basic")
}

android {
    namespace = "com.example.william.my.module.compose"
    // https://developer.android.google.cn/jetpack/androidx/releases/compose-kotlin
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.activity)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.androidx.compose.ui.tooling.preview)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}