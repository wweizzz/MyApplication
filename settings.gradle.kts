pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
            name = "Jcenter"
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/google")
            name = "Google"
        }
        maven {
            url = uri("https://jitpack.io")
            name = "JitPack"
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/public")
            name = "Sonatype"
        }
        maven {
            url = uri("https://storage.flutter-io.cn/download.flutter.io")
            name = "Flutter"
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    /**
     * PREFER_PROJECT(true)--首选项目存储库
     * PREFER_SETTINGS(false)--首选设置存储库
     * FAIL_ON_PROJECT_REPOS(false)--强制设置存储库
     */
    //@Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    //@Suppress("UnstableApiUsage")
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
            name = "Jcenter"
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/google")
            name = "Google"
        }
        maven {
            url = uri("https://jitpack.io")
            name = "JitPack"
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/public")
            name = "Sonatype"
        }
        maven {
            url = uri("https://storage.flutter-io.cn/download.flutter.io")
            name = "Flutter"
        }
        google()
        mavenCentral()
    }
}
rootProject.name = "My Application"

apply {
    from("settings_flutter.gradle")
}

include(":app")
include(":basic:basic_library")
include(":basic:basic_module")
include(":basic:basic_repository")

include(":libs:lib_widget")

include(":libs:lib_volley")
include(":libs:lib_okhttp")
include(":libs:lib_websocket")
include(":libs:lib_retrofit")
include(":libs:lib_download")

include(":libs:lib_eventbus")
include(":libs:lib_ninepatch")
include(":libs:lib_imageloader")

include(":modules:module_flutter")
include(":modules:module_arch")
include(":modules:module_demo")
include(":modules:module_libraries")
include(":modules:module_network")
include(":modules:module_opensource")
include(":modules:module_sample")
include(":modules:module_utils")
include(":modules:module_compose")
