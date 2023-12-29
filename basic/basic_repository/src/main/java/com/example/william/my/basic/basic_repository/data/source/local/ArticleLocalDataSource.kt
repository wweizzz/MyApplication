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
package com.example.william.my.basic.basic_repository.data.source.local

import androidx.lifecycle.LiveData
import com.example.william.my.basic.basic_repository.bean.ArticleData
import com.example.william.my.basic.basic_repository.bean.ArticleListData
import com.example.william.my.basic.basic_repository.data.NetworkResult
import com.example.william.my.basic.basic_repository.data.source.ArticleDataSource
import com.example.william.my.basic.basic_repository.database.dao.ArticleDao
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleLocalDataSource(
    private val articleDao: ArticleDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ArticleDataSource {
    override fun getArticle(page: Int, callback: ArticleDataSource.LoadArticleCallback) {
        TODO("Not yet implemented")
    }

    override fun getArticleLiveData(
        page: Int,
        postValue: (RetrofitResponse<ArticleListData>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getArticleLiveData(page: Int): LiveData<RetrofitResponse<ArticleListData>> {
        TODO("Not yet implemented")
    }

    override fun getArticleSingle(page: Int): Single<RetrofitResponse<ArticleListData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticleSuspend(page: Int): RetrofitResponse<ArticleListData> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticleResult(page: Int): NetworkResult<List<ArticleData>> {
        return withContext(ioDispatcher) {
            try {
                val articles = articleDao.getArticles()
                NetworkResult.Success(articles)
            } catch (e: Exception) {
                NetworkResult.Error(e)
            }
        }
    }

    override fun saveArticles(articles: List<ArticleData>) {
        TODO("Not yet implemented")
    }

    override fun saveArticle(article: ArticleData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllArticles() =
        withContext(ioDispatcher) {
            articleDao.deleteAllArticles()
        }
}
