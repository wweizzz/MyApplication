package com.example.william.my.lib.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * ContentProvider 初始化的顺序：
 * Application.attachBaseContext(super before) -> Application.attachBaseContext(super after) ->
 * ContentProvider.attachInfo(super before) -> ContentProvider.onCreate() -> ContentProvider.attachInfo(super after) ->
 * Application.onCreate(super before) -> Application.onCreate(super after)
 */
class InitProvider : ContentProvider() {

    private val TAG = this.javaClass.simpleName

    override fun attachInfo(context: Context, info: ProviderInfo) {
        println("InitProvider attachInfo before")
        super.attachInfo(context, info)
        println("InitProvider attachInfo after")
    }

    override fun onCreate(): Boolean {
        // 这里可以初始化你需要的代码
        println("InitProvider onCreate init")
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    private fun println(msg: String) {
        Log.e(TAG, msg)
    }
}