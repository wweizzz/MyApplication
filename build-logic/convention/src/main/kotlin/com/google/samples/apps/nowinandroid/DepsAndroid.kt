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

package com.google.samples.apps.nowinandroid

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Configure base Dependencies with Android options
 */
internal fun Project.configureDependenciesAndroid() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        add("implementation", libs.findLibrary("androidx.appCompat").get())
        add("implementation", libs.findLibrary("androidx.appCompat").get())
        add("implementation", libs.findLibrary("androidx.constraintLayout").get())
        add("implementation", libs.findLibrary("androidx.recyclerView").get())
        //
        add("implementation", libs.findLibrary("kotlinx.coroutines").get())
        add("implementation", libs.findLibrary("androidx.core.ktx").get())
        add("implementation", libs.findLibrary("androidx.activity.ktx").get())
        add("implementation", libs.findLibrary("androidx.fragment.ktx").get())
        //add("implementation", libs.findLibrary("autodispose").get())
        add("implementation", libs.findLibrary("rxlifecycle").get())
        add("implementation", libs.findLibrary("brvah").get())
        add("implementation", libs.findLibrary("smartrefresh.layout").get())
        add("implementation", libs.findLibrary("smartrefresh.header").get())
        add("implementation", libs.findLibrary("smartrefresh.footer").get())
        //
        add("testImplementation", libs.findLibrary("junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx-test-ext").get())
        add("androidTestImplementation", libs.findLibrary("androidx-test-espresso").get())

    }
}
