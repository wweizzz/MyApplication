package com.example.william.my.module.sample.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 使用键存储和访问数据。此实现不需要预定义的架构，也不确保类型安全。
 */
class ExamplePreferenceDataStore(val context: Context) {

    // 创建 DataStore
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun getCounter(): Flow<Int> {
        return context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[EXAMPLE_COUNTER] ?: 0
            }
    }

    suspend fun incrementCounter() {
        context.dataStore.edit {
            //it[EXAMPLE_COUNTER] = counter
            val currentCounterValue = it[EXAMPLE_COUNTER] ?: 0
            it[EXAMPLE_COUNTER] = currentCounterValue + 1
        }
    }

    suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object {
        val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
    }
}