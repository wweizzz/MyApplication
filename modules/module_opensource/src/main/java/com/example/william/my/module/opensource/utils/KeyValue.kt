package com.example.william.my.module.opensource.utils

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * MMKV
 * https://github.com/Tencent/MMKV
 */
object KeyValue {

    private lateinit var mmkv: MMKV

    fun initialize(context: Context) {
        val rootDir: String = MMKV.initialize(context)
        println("mmkv root: $rootDir")
    }

    fun mmkv(mmapID: String): KeyValueManager {
        mmkv = MMKV.mmkvWithID(mmapID)
        return KeyValueManager(mmkv)
    }

    class KeyValueManager(val mmkv: MMKV) {
        @Suppress("UNCHECKED_CAST")
        fun put(key: String, value: Any) {
            when (value) {
                is Int -> mmkv.encode(key, value)
                is Long -> mmkv.encode(key, value)
                is Float -> mmkv.encode(key, value)
                is Double -> mmkv.encode(key, value)
                is Boolean -> mmkv.encode(key, value)
                is String -> mmkv.encode(key, value)
                is Parcelable -> mmkv.encode(key, value)
                is MutableSet<*> -> mmkv.encode(key, value as Set<String>)
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> get(key: String, defValue: T): T {
            return when (defValue) {
                is Int -> mmkv.decodeInt(key, defValue) as T
                is Long -> mmkv.decodeLong(key, defValue) as T
                is Float -> mmkv.decodeFloat(key, defValue) as T
                is Double -> mmkv.decodeDouble(key, defValue) as T
                is Boolean -> mmkv.decodeBool(key, defValue) as T
                is String -> mmkv.decodeString(key, defValue) as T
                is MutableSet<*> -> (mmkv.decodeStringSet(key) ?: defValue) as T
                else -> defValue
            }
        }

        fun <T : Parcelable> getParcelable(key: String, tClass: Class<T>): T {
            return mmkv.decodeParcelable(key, tClass) ?: tClass.getDeclaredConstructor()
                .newInstance()
        }

        fun containsKey(key: String): Boolean {
            return mmkv.containsKey(key)
        }

        fun removeKey(key: String) {
            mmkv.removeValueForKey(key)
        }

        fun clearAll() {
            mmkv.clearAll()
        }
    }
}