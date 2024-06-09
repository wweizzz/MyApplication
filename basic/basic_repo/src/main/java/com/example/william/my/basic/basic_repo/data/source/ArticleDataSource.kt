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
package com.example.william.my.basic.basic_repo.data.source

import androidx.lifecycle.LiveData
import com.example.william.my.basic.basic_repo.data.NetworkResult
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single

/**
 * Main entry point for accessing tasks data.
 */
interface ArticleDataSource<ArticleList, ArticleDetail> {

    interface LoadArticleCallback<ArticleDetail> {
        fun onArticleLoaded(articles: List<ArticleDetail>)
        fun onDataNotAvailable()
    }

    fun getArticleCallback(
        page: Int, callback: LoadArticleCallback<ArticleDetail>
    )

    fun getArticleLiveData(
        page: Int,
        postValue: (RetrofitResponse<ArticleList>) -> Unit
    )

    fun getArticleSingle(page: Int): Single<RetrofitResponse<ArticleList>>

    fun getArticleLiveData(page: Int): LiveData<RetrofitResponse<ArticleList>>

    suspend fun getArticleSuspend(page: Int): RetrofitResponse<ArticleList>

    suspend fun getArticleResult(page: Int): NetworkResult<List<ArticleDetail>>

    suspend fun saveArticle(article: ArticleDetail)

    suspend fun saveArticles(articles: List<ArticleDetail>)

    suspend fun deleteAllArticles()
}
