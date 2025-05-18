@file:Suppress("BlockingMethodInNonBlockingContext")

package com.example.william.my.module.sample.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.william.my.module.sample.proto.Settings
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

/**
 * 将数据作为自定义数据类型的实例进行存储。此实现要求您使用协议缓冲区来定义架构，但可以确保类型安全。
 */
class ExampleProtoDataStore(val context: Context) {

    object SettingsSerializer : Serializer<Settings> {

        override val defaultValue: Settings = Settings.getDefaultInstance()

        override suspend fun readFrom(input: InputStream): Settings {
            try {
                return Settings.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override suspend fun writeTo(t: Settings, output: OutputStream) {
            return t.writeTo(output)
        }
    }

    private val Context.dataStore: DataStore<Settings> by dataStore(
        fileName = "settings.pb",
        serializer = SettingsSerializer
    )

    fun getCounter(): Flow<Int> {
        return context.dataStore.data
            .map { settings ->
                // The exampleCounter property is generated from the proto schema.
                settings.exampleCounter
            }
    }

    suspend fun incrementCounter() {
        context.dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setExampleCounter(currentSettings.exampleCounter + 1)
                .build()
        }
    }

    suspend fun clear() {
        context.dataStore.updateData {
            it.toBuilder()
                .clear()
                .build()
        }
    }
}