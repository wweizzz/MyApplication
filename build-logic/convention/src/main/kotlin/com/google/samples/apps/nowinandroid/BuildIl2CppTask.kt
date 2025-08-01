package com.google.samples.apps.nowinandroid

import com.android.build.gradle.BaseExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem
import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec
import java.io.File
import java.io.FileInputStream
import java.util.Properties
import javax.inject.Inject
import kotlin.collections.forEach
import kotlin.io.copyTo
import kotlin.jvm.java

abstract class BuildIl2CppTask : DefaultTask() {

    @get:Internal
    lateinit var workingDir: String

    @get:Internal
    lateinit var configuration: String

    @get:Internal
    lateinit var architecture: String

    @get:Internal
    lateinit var abi: String

    @get:Internal
    lateinit var staticLibraries: Array<String>

    @get:Inject
    abstract val execOperations: ExecOperations

    @Internal
    fun getSdkDir(): String {
        val local = Properties()
        local.load(FileInputStream("${project.rootDir}/local.properties"))
        return local.getProperty("sdk.dir")
    }

    @Internal
    fun getNdkDirectory(): String {
        val android = project.extensions.getByType(BaseExtension::class.java)
        return android.ndkDirectory.absolutePath
    }

    @TaskAction
    fun executeCommand() {
        val commandLineArgs = mutableListOf(
            "--compile-cpp",
            "--platform=Android",
            "--architecture=$architecture",
            "--outputpath=${File(workingDir, "src/main/jniLibs/$abi/libil2cpp.so")}",
            "--baselib-directory=${File(workingDir, "src/main/jniStaticLibs/$abi")}",
            "--configuration=$configuration",
            "--dotnetprofile=unityaot-linux",
            "--profiler-report",
            "--profiler-output-file=${
                File(
                    workingDir,
                    "build/il2cpp_${abi}_${configuration}/il2cpp_conv.traceevents"
                )
            }",
            "--print-command-line",
            "--data-folder=${
                File(
                    workingDir,
                    "src/main/Il2CppOutputProject/Source/il2cppOutput/data"
                )
            }",
            "--generatedcppdir=${
                File(
                    workingDir,
                    "src/main/Il2CppOutputProject/Source/il2cppOutput"
                )
            }",
            "--cachedirectory=${
                File(
                    workingDir,
                    "build/il2cpp_${abi}_${configuration}/il2cpp_cache"
                )
            }",
            "--tool-chain-path=${getNdkDirectory()}"
        )

        staticLibraries.forEach { fileName ->
            commandLineArgs.add(
                "--additional-libraries=${
                    File(
                        workingDir,
                        "src/main/jniStaticLibs/$abi/$fileName"
                    )
                }"
            )
        }

        val executableExtension = if (org.gradle.internal.os.OperatingSystem.current().isWindows) {
            ".exe"
        } else {
            ""
        }

        val il2cppPath = File(
            workingDir,
            "src/main/Il2CppOutputProject/IL2CPP/build/deploy/il2cpp$executableExtension"
        )

        execOperations.exec {
            executable = il2cppPath.absolutePath
            args = commandLineArgs
            environment("ANDROID_SDK_ROOT", getSdkDir())
        }

        val symSoFile = File(workingDir, "src/main/jniLibs/$abi/libil2cpp.sym.so")
        symSoFile.delete()

        val dbgSoFile = File(workingDir, "src/main/jniLibs/$abi/libil2cpp.dbg.so")
        val symbolsDir = File(workingDir, "symbols/$abi/libil2cpp.so")
        dbgSoFile.copyTo(symbolsDir, overwrite = true)
    }
}