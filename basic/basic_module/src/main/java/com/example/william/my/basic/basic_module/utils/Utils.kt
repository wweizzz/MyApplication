package com.example.william.my.basic.basic_module.utils

import android.util.Log
import java.util.Locale

object Utils {

    private val TAG = this.javaClass.simpleName

    init {
        //TAG = Throwable().stackTrace[1].fileName
    }

    ///////////////////////////////////////////////////////////////////////////
    // Logcat
    ///////////////////////////////////////////////////////////////////////////

    //规定每段显示的长度
    private const val MAX_LENGTH = 4000

    fun logcat(tag: String, msg: String) {
        var temp: String
        var index = 0
        while (index < msg.length) {
            // java的字符不允许指定超过总的长度end
            temp = if (msg.length <= index + MAX_LENGTH) {
                msg.substring(index)
            } else {
                msg.substring(index, index + MAX_LENGTH)
            }
            index += MAX_LENGTH
            Log.e(tag, temp.trim { it <= ' ' })
        }
    }

    private fun buildMessage(format: String, vararg args: Any): String {
        val msg = String.Companion.format(Locale.US, format, *args)
        val trace = Throwable().fillInStackTrace().stackTrace
        var caller = "<unknown>"
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (i in 2 until trace.size) {
            val clazz = trace[i].className
            if (clazz != TAG) {
                var callingClass = trace[i].className
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1)
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1)
                caller = callingClass + "." + trace[i].methodName
                break
            }
        }
        return String.Companion.format(
            Locale.US,
            "[%d] %s: %s",
            Thread.currentThread().id,
            caller,
            msg
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // Toast
    ///////////////////////////////////////////////////////////////////////////

    fun toast(msg: String?) {

    }
}