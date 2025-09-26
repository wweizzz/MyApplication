package com.example.william.my.module.sample.activity.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.utils.Utils
import com.google.gson.Gson

/**
 * kotlin 内联函数
 */
class MyInlineActivity : BasicResponseActivity() {

    data class MyData(var value: String? = "") {
        fun string(): String {
            return Gson().toJson(this)
        }
    }

    private fun innerFun() {
        val mData = MyData()

        /**
         * with 函数
         * 回值由return表达式指定或作用域内最后一行
         */
        with(mData) {
            value = "with"
        }

        /**
         * let 函数
         * 返回值由return表达式指定或作用域内最后一行
         */
        val let: String = mData.let { data ->
            data.value = "let"
            "let"
        }
        println("let $let")

        /**
         * run 函数
         * 返回值由return表达式指定或作用域内最后一行
         */
        val run: String = mData.run {
            mData.value = "run"
            "run"
        }
        println("run $run")

        /**
         * also 函数
         * 返回值是调用的对象本身
         */
        val alsoData: MyData = mData.also { data ->
            data.value = "also"
        }
        println(alsoData.string())

        /**
         * apply 函数
         * 返回值是调用的对象本身
         */
        val applyData: MyData = mData.apply {
            value = "apply"
        }
        println(applyData.string())
    }

    //==============================================================================================

    private inline fun <reified T : Activity> Activity.startActivity(context: Context) {
        startActivity(Intent(context, T::class.java))
    }

    /**
     * reified 关键字
     */
    fun reifiedFun() {
        startActivity<MyInlineActivity>(this)
    }

    //==============================================================================================

    private fun <T> T.mAlso(block: (T) -> Unit): T {
        block(this)
        return this
    }

    private fun <T> T.mApply(block: T.() -> Unit): T {
        block()
        return this
    }

    private fun <T> T.mStandard(block: () -> Unit): T {
        block()
        return this
    }

    /**
     * 扩展函数
     */
    fun expandFun() {
        val mData = MyData()

        val alsoData: MyData = mData.mAlso { data ->
            data.value = "also"
        }
        println(alsoData.string())

        val applyData: MyData = mData.mApply {
            value = "apply"
        }
        println(applyData.string())

        val standardData: MyData = mData.mStandard {
            mData.string()
        }
        println(standardData.string())
    }

    private fun println(msg: String) {
        Utils.logcat(TAG, msg)
    }
}