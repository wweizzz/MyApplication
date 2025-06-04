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
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.util.Locale

class AndroidEventBusConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "kotlin-kapt")
            extensions.configure<KaptExtension> {
                val eventBusClassPath = "com.example.william.my" +
                        project.path.replace(":", ".")
                val eventBusClassName = "My" +
                        project.name.substringAfter("_", project.name)
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + "EventBusIndex"
                println("eventBusClassPath : $eventBusClassPath")
                println("eventBusClassName : $eventBusClassName")
                arguments {
                    arg("eventBusIndex", "$eventBusClassPath.$eventBusClassName")
                }
            }
            dependencies {
                add("implementation", libs.findLibrary("eventbus").get())
                add("kapt", libs.findLibrary("eventbus.processor").get())
            }
        }
    }
}