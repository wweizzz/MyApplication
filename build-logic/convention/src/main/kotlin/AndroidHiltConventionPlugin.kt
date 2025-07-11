/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.google.samples.apps.nowinandroid.libs
import dagger.hilt.android.plugin.HiltExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "kotlin-kapt")
            apply(plugin = "com.google.dagger.hilt.android")
            extensions.configure<KaptExtension> {
                // Allow references to generated code
                correctErrorTypes = true
            }
            // https://github.com/alibaba/ARouter/issues/1051
            extensions.configure<HiltExtension>{
                enableAggregatingTask = false
            }
            dependencies {
                "implementation"(libs.findLibrary("androidx.hilt.android").get())
                "kapt"(libs.findLibrary("androidx.hilt.compiler").get())
            }
        }
    }
}
