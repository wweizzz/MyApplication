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
package com.example.william.my.basic.basic_repo.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.william.my.basic.basic_data.bean.ArticleBase
import com.example.william.my.basic.basic_data.data.NetworkResult
import com.example.william.my.basic.basic_data.data.source.ArticleDataSource
import com.example.william.my.basic.basic_repo.api.ArticleApi
import com.example.william.my.basic.basic_repo.bean.ArticleBean
import com.example.william.my.core.retrofit.callback.RetrofitLiveDataCallback
import com.example.william.my.core.retrofit.callback.RetrofitResponseCallback
import com.example.william.my.core.retrofit.exception.ApiException
import com.example.william.my.core.retrofit.function.HttpResultFunction
import com.example.william.my.core.retrofit.function.ServerResultFunction
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object ArticleRemoteDataSource : ArticleDataSource {

    private var articleApi = RetrofitHelper.buildApi(ArticleApi::class.java)

    override fun getArticle(page: Int, callback: ArticleDataSource.LoadArticleCallback) {
        articleApi.getArticleSingle(page)
            .onErrorResumeNext(HttpResultFunction())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitResponseCallback<ArticleBean>() {
                override fun onResponse(response: ArticleBean) {
                    super.onResponse(response)
                    if (response.datas.isNotEmpty()) {
                        callback.onArticleLoaded(response.datas)
                    } else {
                        callback.onDataNotAvailable()
                    }
                }

                override fun onFailure(e: ApiException) {
                    super.onFailure(e)
                    callback.onDataNotAvailable()
                }
            })
    }

    override fun getArticleLiveData(
        page: Int,
        postValue: (RetrofitResponse<ArticleBean>) -> Unit
    ) {
        articleApi.getArticleSingle(page)
            .map(ServerResultFunction())
            .onErrorResumeNext(HttpResultFunction())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                RetrofitLiveDataCallback<ArticleBean>() {
                override fun onPostValue(value: RetrofitResponse<ArticleBean>) {
                    super.onPostValue(value)
                    postValue(value)
                }
            })
    }

    override fun getArticleLiveData(page: Int): LiveData<RetrofitResponse<ArticleBean>> {
        val liveData: MutableLiveData<RetrofitResponse<ArticleBean>> =
            MutableLiveData()
        articleApi.getArticleSingle(page)
            .map(ServerResultFunction())
            .onErrorResumeNext(HttpResultFunction())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :
                RetrofitLiveDataCallback<ArticleBean>() {
                override fun onPostValue(value: RetrofitResponse<ArticleBean>) {
                    super.onPostValue(value)
                    liveData.postValue(value)
                }
            })
        return liveData
    }

    override fun getArticleSingle(page: Int): Single<RetrofitResponse<ArticleBean>> {
        return articleApi.getArticleSingle(page)
            .onErrorResumeNext(HttpResultFunction())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun getArticleSuspend(page: Int): RetrofitResponse<ArticleBean> {
        return articleApi.getArticleSuspend(page)
    }

    override suspend fun getArticleResult(page: Int): NetworkResult<List<ArticleBase>> {
        return try {
            val response = articleApi.getArticleSuspend(page)
            NetworkResult.Success(response.data!!.datas)
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    override fun saveArticles(articles: List<ArticleBase>) {

    }

    override fun saveArticle(article: ArticleBase) {

    }

    override suspend fun deleteAllArticles() {
        // NO-OP
    }
}
