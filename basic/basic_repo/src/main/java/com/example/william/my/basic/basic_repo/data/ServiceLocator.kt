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
package com.example.william.my.basic.basic_repo.data

import android.content.Context
import androidx.room.Room
import com.example.william.my.basic.basic_repo.api.ArticleApi
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleDataSource
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.basic.basic_repo.data.source.DefaultArticleRepository
import com.example.william.my.basic.basic_repo.data.source.local.ArticleLocalDataSource
import com.example.william.my.basic.basic_repo.data.source.remote.ArticleRemoteDataSource
import com.example.william.my.basic.basic_repo.database.ArticleDatabase
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private val lock = Any()

    @Volatile
    private var articleApi: ArticleApi? = null

    @Volatile
    private var articleDatabase: ArticleDatabase? = null

    @Volatile
    private var articleRepository: ArticleRepository<ArticleData, ArticleDetailData>? = null

    fun provideArticleApi(): ArticleApi {
        synchronized(this) {
            return articleApi ?: createApi()
        }
    }

    fun provideArticleDatabase(context: Context): ArticleDatabase {
        synchronized(this) {
            return articleDatabase ?: createDataBase(context)
        }
    }

    fun provideArticleRepository(context: Context): ArticleRepository<ArticleData, ArticleDetailData> {
        synchronized(this) {
            return articleRepository ?: createArticleRepository(context)
        }
    }

    private fun createApi(): ArticleApi {
        return RetrofitHelper.buildApi(ArticleApi::class.java)
    }

    private fun createDataBase(
        context: Context,
        inMemory: Boolean = false
    ): ArticleDatabase {
        val result = if (inMemory) {
            // Use a faster in-memory database for tests
            Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        } else {
            // Real database using SQLite
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java, "Articles.db"
            ).build()
        }
        articleDatabase = result
        return result
    }

    private fun createArticleLocalDataSource(context: Context): ArticleDataSource<ArticleData, ArticleDetailData> {
        val database = articleDatabase ?: createDataBase(context)
        return ArticleLocalDataSource(database.articleDao())
    }

    private fun createArticleRepository(context: Context): ArticleRepository<ArticleData, ArticleDetailData> {
        val newRepo =
            DefaultArticleRepository(ArticleRemoteDataSource, createArticleLocalDataSource(context))
        articleRepository = newRepo
        return newRepo
    }

    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                ArticleRemoteDataSource.deleteAllArticles()
            }
            // Clear all data to avoid test pollution.
            articleDatabase?.apply {
                clearAllTables()
                close()
            }
            articleDatabase = null
            articleRepository = null
        }
    }
}

