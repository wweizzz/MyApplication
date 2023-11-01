plugins {
    id("nowinandroid.android.library")
    id("nowinandroid.android.basic")
    id("org.greenrobot.greendao")
    id("io.objectbox")
    //id("nowinandroid.android.greendao")
    //id("nowinandroid.android.objectbox")
}

android {
    namespace = "com.example.william.my.module.opensource"
    resourcePrefix("open_")
    greendao {
        // 架构版本
        schemaVersion = 1
        // 生成的DAO、DaoMaster和DaoSession的包名称
        daoPackage = "com.example.william.my.module.opensource.greendao.dao"
        // 是否自动生成单元测试
        generateTests = true
        // 存储生成的单元测试的基目录
        targetGenDir("src/main/java")
    }
    kapt {
        arguments {
            arg("objectbox.debug", true)
            arg("objectbox.modelPath", "$projectDir/objectbox-models/default.json")
        }
    }
}

dependencies {
    implementation(libs.badgeview)
    implementation(libs.banner)
    implementation(libs.citypicker)
    implementation(libs.countdownview)
    implementation(libs.easyfloat)
    implementation(libs.tablayout)
    implementation(libs.lottie)
    implementation(libs.photoview)
    implementation(libs.pickerview)
    implementation(libs.popwindow)
    implementation(libs.shadowlayout)
    implementation(libs.svgaPlayer)
    implementation(libs.swipelayout)


    implementation(libs.mmkv)
    implementation(libs.permission)
    implementation(libs.loadsir)
    implementation(libs.greendao)
    implementation(libs.objectbox)

    implementation(files("lib/oaid_sdk_2.1.0.aar"))
}

// https://github.com/greenrobot/greenDAO/issues/1110
tasks.matching {
    it.name.matches(Regex("compile\\w*Kotlin"))
}.configureEach {
    dependsOn("greendao")
}
//tasks.configureEach { task ->
//    if (task.name.matches("compile\\w*Kotlin")) {
//        task.dependsOn('greendao')
//    }
//}