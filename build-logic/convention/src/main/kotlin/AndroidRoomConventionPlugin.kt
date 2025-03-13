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
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.process.CommandLineArgumentProvider
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.io.File

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
                //apply("androidx.room")
            }
            extensions.configure<KaptExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                arguments {
                    arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
                }
            }
            //extensions.configure<RoomExtension> {
            //    // The schemas directory contains a schema file for each version of the Room database.
            //    // This is required to enable Room auto migrations.
            //    // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
            //    schemaDirectory("$projectDir/schemas")
            //}
            dependencies {
                add("implementation", libs.findLibrary("androidx.room").get())
                add("implementation", libs.findLibrary("androidx.room.ktx").get())
                add("implementation", libs.findLibrary("androidx.room.rxjava3").get())
                add("implementation", libs.findLibrary("androidx.room.paging").get())
                add("kapt", libs.findLibrary("androidx.room.compiler").get())
            }
        }
    }

    /**
     * https://issuetracker.google.com/issues/132245929
     * [Export schemas](https://developer.android.com/training/data-storage/room/migrating-db-versions#export-schemas)
     */
    class RoomSchemaArgProvider(
        @get:InputDirectory
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val schemaDir: File,
    ) : CommandLineArgumentProvider {
        override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
    }
}