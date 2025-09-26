package com.example.william.my.module.opensource.activity3

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.utils.Utils
import com.tencent.mmkv.MMKV

/**
 * https://github.com/Tencent/MMKV/wiki/android_tutorial_cn
 */
@Route(path = RouterPath.Opensource.MMKV)
class MMKVActivity : BasicResponseActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initMMKV()
    }

    private fun initMMKV() {
        // 配置 MMKV 根目录
        val rootDir: String = MMKV.initialize(this)
        Utils.logcat(TAG, "mmkv root: $rootDir")

        // MMKV 全局实例
        val kv: MMKV = MMKV.defaultMMKV()

        kv.encode("bool", true)
        println("bool: " + kv.decodeBool("bool"))

        kv.encode("int", Int.MIN_VALUE)
        println("int: " + kv.decodeInt("int"))

        kv.encode("long", Long.MAX_VALUE)
        println("long: " + kv.decodeLong("long"))

        kv.encode("float", -3.14f)
        println("float: " + kv.decodeFloat("float"))

        kv.encode("double", Double.MIN_VALUE)
        println("double: " + kv.decodeDouble("double"))

        kv.encode("string", "Hello from mmkv")
        println("string: " + kv.decodeString("string"))

        kv.encode(
            "bytes",
            byteArrayOf('m'.code.toByte(), 'm'.code.toByte(), 'k'.code.toByte(), 'v'.code.toByte())
        )
        println("bytes: " + kv.decodeBytes("bytes").toString())
    }
}