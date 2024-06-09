/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.william.my.basic.basic_repo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.database.dao.ArticleDao

/**
 * The Room Database that contains the Article table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(
    entities = [ArticleDetailData::class],
    version = 1,
    exportSchema = false,
)
abstract class ArticleDatabase : RoomDatabase() {

    companion object {

        private const val DB_NAME = "Articles.db"

        private var instance: ArticleDatabase? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: createDataBase(context).also {
                    instance = it
                }
            }

        private fun createDataBase(
            context: Context,
            inMemory: Boolean = false
        ): ArticleDatabase {
            val result = if (inMemory) {
                // 使用更快的内存中数据库进行测试
                // Use a faster in-memory database for tests
                Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java
                )
                    .allowMainThreadQueries()
                    .build()
            } else {
                // 使用SQLite的真实数据库
                // Real database using SQLite
                Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java, DB_NAME
                ).build()
            }
            return result
        }
    }

    abstract fun articleDao(): ArticleDao
}
