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

package com.example.william.my.basic.basic_repo.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData

/**
 * Data Access Object for the article table.
 */
@Dao
interface ArticleDao {

    /**
     * Select all articles from the Articles table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Articles")
    suspend fun getArticles(): List<ArticleDetailData>

    /**
     * Insert articles in the database. If the articlse already exists, replace it.
     *
     * @param articles the article to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleDetailData>)

    /**
     * Insert a article in the database. If the article already exists, replace it.
     *
     * @param article the article to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleDetailData)

    /**
     * Delete all articles.
     */
    @Query("DELETE FROM Articles")
    suspend fun deleteAllArticles()

    /**
     * PagingSource
     */
    @Query("Select * From Articles Order By page")
    fun getArticlesPagingSource(): PagingSource<Int, ArticleDetailData>
}