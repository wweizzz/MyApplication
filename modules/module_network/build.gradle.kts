plugins {
    alias(libs.plugins.nowinandroid.android.library)
    alias(libs.plugins.nowinandroid.android.arouter)
    alias(libs.plugins.nowinandroid.android.eventbus)
}

android {
    namespace = "com.example.william.my.module.network"
    resourcePrefix("network_")
    //Netty
    //packaging {
    //    resources.excludes.add("META-INF/INDEX.LIST")
    //    resources.excludes.add("META-INF/io.netty.versions.properties")
    //}
}

dependencies {
    implementation(project(":basic:basic_lib"))
    implementation(project(":basic:basic_module"))

    implementation(project(":basic:basic_data"))

    implementation(libs.coil)
    implementation(libs.slf4j)
    implementation(libs.netty)
    implementation(libs.nanohttpd)
    implementation(libs.websocket)
    implementation(project(":libs:lib_volley"))
    implementation(project(":libs:lib_okhttp"))
    implementation(project(":libs:lib_retrofit"))
    implementation(project(":libs:lib_download"))
    implementation(project(":libs:lib_websocket"))

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.negotiation)

    implementation(libs.ktor.serialization.gson)
    implementation(libs.ktor.serialization.json)
}