/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.google.samples.apps.nowinandroid.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.gradlePlugin.android)
    compileOnly(libs.gradlePlugin.kotlin)
    compileOnly(libs.gradlePlugin.protobuf)
    compileOnly(libs.gradlePlugin.greendao)
    compileOnly(libs.gradlePlugin.objectbox)

    compileOnly(libs.gradlePlugin.hilt)
    //compileOnly(libs.gradlePlugin.room)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "nowinandroid.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "nowinandroid.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLint") {
            id = "nowinandroid.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("androidARouter") {
            id = "nowinandroid.android.arouter"
            implementationClass = "AndroidARouterConventionPlugin"
        }
        register("androidEventBus") {
            id = "nowinandroid.android.eventbus"
            implementationClass = "AndroidEventBusConventionPlugin"
        }
        register("androidHilt") {
            id = "nowinandroid.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        //
        register("androidProtobuf") {
            id = "nowinandroid.android.protobuf"
            implementationClass = "AndroidProtobufConventionPlugin"
        }
        register("androidRoom") {
            id = "nowinandroid.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        //
        register("androidGreenDao") {
            id = "nowinandroid.android.greendao"
            implementationClass = "AndroidGreenDaoConventionPlugin"
        }
        register("androidObjectBox") {
            id = "nowinandroid.android.objectbox"
            implementationClass = "AndroidObjectBoxConventionPlugin"
        }
    }
}
