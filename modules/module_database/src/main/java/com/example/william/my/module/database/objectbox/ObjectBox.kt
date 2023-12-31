package com.example.william.my.module.database.objectbox

import android.content.Context
import android.util.Log
import com.example.william.my.module.database.BuildConfig
import io.objectbox.BoxStore
import io.objectbox.android.Admin
import io.objectbox.exception.DbException
import io.objectbox.exception.FileCorruptException

object ObjectBox {

    private val TAG: String = this.javaClass.simpleName

    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        // On Android make sure to pass a Context when building the Store.
        boxStore = try {
            MyObjectBox.builder()
                .androidContext(context.applicationContext)
                .build()
        } catch (e: DbException) {
            if (e.javaClass == DbException::class.java || e is FileCorruptException) {
                // Demonstrate handling issues caused by devices with a broken file system
                Log.w(TAG, "File corrupt, trying previous data snapshot...", e)
                return
            } else {
                // Failed to build BoxStore due to developer error.
                throw e
            }
        }

        if (BuildConfig.DEBUG) {
            Log.d(
                TAG, String.format(
                    "Using ObjectBox %s (%s)",
                    BoxStore.getVersion(), BoxStore.getVersionNative()
                )
            )
            // Enable ObjectBox Admin on debug builds.
            // https://docs.objectbox.io/data-browser
            Admin(boxStore).start(context.applicationContext)
        }
    }
}