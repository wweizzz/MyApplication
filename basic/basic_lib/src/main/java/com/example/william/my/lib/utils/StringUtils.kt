package com.example.william.my.lib.utils

object StringUtils {

    fun listToString(list: List<String>?, separator: String): String {
        return if (list.isNullOrEmpty()) {
            ""
        } else {
            val sb = StringBuilder()
            for (i in list.indices) {
                sb.append(list[i]).append(separator)
            }
            sb.substring(0, sb.toString().length - 1)
        }
    }
}