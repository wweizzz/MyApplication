plugins {
    id("nowinandroid.android.feature")
}

android {
    namespace = "com.example.william.my.module.network"
    resourcePrefix("network_")
    // Netty
    //packaging {
    //    resources.excludes.add("META-INF/INDEX.LIST")
    //    resources.excludes.add("META-INF/io.netty.versions.properties")
    //}
}

dependencies {
    implementation(libs.coil)
    implementation(libs.netty)
    implementation(libs.nanohttpd)
    implementation(libs.websocket)
    implementation(project(":libs:lib_volley"))
    implementation(project(":libs:lib_okhttp"))
    implementation(project(":libs:lib_retrofit"))
    implementation(project(":libs:lib_download"))
    implementation(project(":libs:lib_websocket"))
    implementation("org.slf4j:slf4j-nop:2.0.7")
}