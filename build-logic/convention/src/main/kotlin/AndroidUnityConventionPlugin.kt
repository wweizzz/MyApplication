import com.android.build.gradle.LibraryExtension
import com.google.samples.apps.nowinandroid.BuildIl2CppTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

class AndroidUnityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    ndk {
                        abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a"))
                    }
                    consumerProguardFiles("proguard-unity.txt", "proguard-user.txt")
                }
                packaging {
                    androidResources {
                        val unityStreamingAssets = ""
                        noCompress.addAll(
                            listOf(
                                ".unity3d",
                                ".ress",
                                ".resource",
                                ".obb",
                                ".bundle",
                                ".unityexp"
                            ) + unityStreamingAssets.split(',')
                        )
                        ignoreAssetsPattern =
                            "!.svn:!.git:!.ds_store:!*.scc:!CVS:!thumbs.db:!picasa.ini:!*~"
                    }
                    jniLibs {
                        useLegacyPackaging = true
                        keepDebugSymbols.addAll(
                            arrayOf("*/armeabi-v7a/*.so", "*/arm64-v8a/*.so")
                        )
                    }
                }

                sourceSets {
                    named("main") {
                        jniLibs.setSrcDirs(listOf("src/main/Il2CppOutputProject"))
                    }
                }

                lint {
                    abortOnError = false
                }
            }

            dependencies {
                add(
                    "implementation",
                    fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar")))
                )
            }

            registerBuildIl2CppTasks()

            afterEvaluate {
                configureUnityProject()
            }
        }
    }

    private fun Project.registerBuildIl2CppTasks() {
        val projectDir = projectDir.toString().replace("\\", "/")

        tasks.register<BuildIl2CppTask>("buildIl2CppArmv7") {
            group = "Build"
            description = "Compiles IL2CPP for armeabi-v7a"
            workingDir = projectDir
            configuration = "Release"
            architecture = "armv7"
            abi = "armeabi-v7a"
            staticLibraries = emptyArray()
        }

        tasks.register<BuildIl2CppTask>("buildIl2CppArm64") {
            group = "Build"
            description = "Compiles IL2CPP for arm64-v8a"
            workingDir = projectDir
            configuration = "Release"
            architecture = "arm64"
            abi = "arm64-v8a"
            staticLibraries = emptyArray()
        }

        tasks.register("buildAllIl2Cpp") {
            group = "Build"
            description = "Aggregates all IL2CPP build tasks"
            dependsOn("buildIl2CppArmv7", "buildIl2CppArm64")
        }
    }

    private fun Project.configureUnityProject() {
        val unityProject = project(":lib_unity")

        unityProject.tasks.named("mergeDebugJniLibFolders").configure {
            dependsOn("buildAllIl2Cpp")
        }

        unityProject.tasks.named("mergeReleaseJniLibFolders").configure {
            dependsOn("buildAllIl2Cpp")
        }
    }
}