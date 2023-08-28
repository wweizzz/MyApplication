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
package com.example.william.my.basic.basic_repository.data.source

import androidx.lifecycle.LiveData
import com.example.william.my.basic.basic_repository.bean.Article
import com.example.william.my.basic.basic_repository.bean.ArticleData
import com.example.william.my.basic.basic_repository.data.NetworkResult
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single

/**
 * Main entry point for accessing tasks data.
 */
interface ArticleDataSource {

    interface LoadArticleCallback {
        fun onArticleLoaded(article: List<Article>)
        fun onDataNotAvailable()
    }

    fun getArticle(page: Int, callback: LoadArticleCallback)

    fun getArticleLiveData(page: Int, postValue: (RetrofitResponse<ArticleData>) -> Unit)

    fun getArticleLiveData(page: Int): LiveData<RetrofitResponse<ArticleData>>

    fun getArticleSingle(page: Int): Single<RetrofitResponse<ArticleData>>

    suspend fun getArticleSuspend(page: Int): RetrofitResponse<ArticleData>

    suspend fun getArticleResult(page: Int): NetworkResult<List<Article>>

    fun saveArticles(articles: List<Article>)

    fun saveArticle(articles: Article)

    suspend fun deleteAllArticles()
}
