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
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.NetworkResult
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Default implementation of [ArticleRepository]. Single entry point for managing Articles' data.
 */
class DefaultArticleRepository(
    private val articlesRemoteDataSource: ArticleDataSource<ArticleData, ArticleDetailData>,
    private val articlesLocalDataSource: ArticleDataSource<ArticleData, ArticleDetailData>?,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ArticleRepository<ArticleData, ArticleDetailData> {

    override fun getArticle(
        page: Int,
        callback: ArticleRepository.LoadArticleCallback<ArticleDetailData>
    ) {
        articlesRemoteDataSource.getArticleCallback(
            page, object : ArticleDataSource.LoadArticleCallback<ArticleDetailData> {
                override fun onArticleLoaded(articles: List<ArticleDetailData>) {
                    callback.onArticleLoaded(articles)
                }

                override fun onDataNotAvailable() {
                    callback.onDataNotAvailable()
                }
            })
    }

    /**
     * ArticleLiveDataViewModel
     */
    override fun getArticleLiveData(
        page: Int,
        postValue: (RetrofitResponse<ArticleData>) -> Unit
    ) {
        articlesRemoteDataSource.getArticleLiveData(page, postValue)
    }

    /**
     * ArticleLiveDataViewModel
     */
    override fun getArticleLiveData(page: Int): LiveData<RetrofitResponse<ArticleData>> {
        return articlesRemoteDataSource.getArticleLiveData(page)
    }

    /**
     * Single
     * ArticleLiveDataViewModel
     */
    override fun getArticleSingle(page: Int): Single<RetrofitResponse<ArticleData>> {
        return articlesRemoteDataSource.getArticleSingle(page)
    }

    /**
     * Continuation
     * ArticleStateFlowViewModel
     */
    override suspend fun getArticleSuspend(page: Int): RetrofitResponse<ArticleData> {
        return articlesRemoteDataSource.getArticleSuspend(page)
    }

    override suspend fun getArticleResult(
        page: Int,
        forceUpdate: Boolean
    ): NetworkResult<List<ArticleDetailData>> {
        // Set app as busy while this function executes.
        if (forceUpdate) {
            try {
                updateArticlesFromRemoteDataSource(page)
            } catch (ex: Exception) {
                return NetworkResult.Error(ex)
            }
        }
        return articlesLocalDataSource?.getArticleResult(page)!!
    }

    private suspend fun updateArticlesFromRemoteDataSource(page: Int) {
        val remoteArticles = articlesRemoteDataSource.getArticleResult(page)
        if (remoteArticles is NetworkResult.Success) {
            // Real apps might want to do a proper sync, deleting, modifying or adding each task.
            articlesLocalDataSource?.deleteAllArticles()
            val articles = remoteArticles as NetworkResult.Success<List<ArticleDetailData>>
            articlesLocalDataSource?.saveArticles(articles.data)
        } else if (remoteArticles is NetworkResult.Error) {
            val articles = remoteArticles as NetworkResult.Error
            throw articles.exception
        }
    }
}
