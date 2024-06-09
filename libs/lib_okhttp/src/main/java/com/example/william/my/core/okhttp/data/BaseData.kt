package com.example.william.my.core.okhttp.data

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

/**
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Parcelize
open class BaseData : Parcelable {
    fun string(): String {
        return Gson().toJson(this)
    }
}