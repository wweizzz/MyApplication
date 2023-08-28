package com.example.william.my.core.okhttp.data

import com.google.gson.Gson

/**
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
open class BaseData {
    fun string(): String {
        return Gson().toJson(this)
    }
}