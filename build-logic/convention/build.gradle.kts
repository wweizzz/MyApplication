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

plugins {
    `kotlin-dsl`
    //alias(libs.plugins.android.lint)
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
    compileOnly(libs.gradlePlugin.compose)
    compileOnly(libs.gradlePlugin.protobuf)

    compileOnly(libs.gradlePlugin.greendao)
    compileOnly(libs.gradlePlugin.objectbox)

    compileOnly(libs.gradlePlugin.ksp)
    compileOnly(libs.gradlePlugin.hilt)
    compileOnly(libs.gradlePlugin.room)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.nowinandroid.android.application.asProvider().get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = libs.plugins.nowinandroid.android.application.compose.get().pluginId
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.nowinandroid.android.library.asProvider().get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.nowinandroid.android.library.compose.get().pluginId
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLint") {
            id = libs.plugins.nowinandroid.android.lint.get().pluginId
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("androidTest") {
            id = libs.plugins.nowinandroid.android.test.get().pluginId
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidARouter") {
            id = libs.plugins.nowinandroid.android.arouter.get().pluginId
            implementationClass = "AndroidARouterConventionPlugin"
        }
        register("androidEventBus") {
            id = libs.plugins.nowinandroid.android.eventbus.get().pluginId
            implementationClass = "AndroidEventBusConventionPlugin"
        }

        //
        register("androidProtobuf") {
            id = libs.plugins.nowinandroid.android.protobuf.get().pluginId
            implementationClass = "AndroidProtobufConventionPlugin"
        }
        register("androidHilt") {
            id = libs.plugins.nowinandroid.android.hilt.get().pluginId
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = libs.plugins.nowinandroid.android.room.get().pluginId
            implementationClass = "AndroidRoomConventionPlugin"
        }

        //
        register("androidGreenDao") {
            id = libs.plugins.nowinandroid.android.greendao.get().pluginId
            implementationClass = "AndroidGreenDaoConventionPlugin"
        }
        register("androidObjectBox") {
            id = libs.plugins.nowinandroid.android.objectbox.get().pluginId
            implementationClass = "AndroidObjectBoxConventionPlugin"
        }
    }
}
