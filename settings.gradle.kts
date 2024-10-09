pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/google")
            name = "Google"
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
            name = "Central&Jcenter"
        }
        maven {
            url = uri("https://jitpack.io")
            name = "JitPack"
        }
        maven {
            url = uri("https://tencent-tds-maven.pkg.coding.net/repository/shiply/repo")
            name = "Shiply"
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
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    @Suppress("UnstableApiUsage")
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/google")
            name = "Google"
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
            name = "Central&Jcenter"
        }
        maven {
            url = uri("https://jitpack.io")
            name = "JitPack"
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

include(":app")
include(":basic:basic_lib")
include(":basic:basic_module")

include(":basic:basic_data")
include(":basic:basic_repo")

include(":libs:lib_okhttp")
include(":libs:lib_retrofit")

include(":libs:lib_volley")
include(":libs:lib_websocket")

include(":libs:lib_download")
include(":libs:lib_imageloader")

include(":libs:lib_widget")

include(":libs:lib_eventbus")
include(":libs:lib_ninepatch")

include(":modules:module_demo")
include(":modules:module_widget")
include(":modules:module_libraries")

include(":modules:module_opensource")
include(":modules:module_database")
include(":modules:module_utils")

//:basic:basic_data
include(":modules:module_network")
//:basic:basic_data
include(":modules:module_sample")

//:basic:basic_repo
include(":modules:module_room")
//:basic:basic_repo
include(":modules:module_arch")

include(":modules:module_compose")

apply {
    from("configs_flutter.gradle")
}
include(":modules:module_flutter")