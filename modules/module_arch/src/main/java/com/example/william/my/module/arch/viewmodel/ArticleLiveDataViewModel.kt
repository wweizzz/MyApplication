/*
 * Copyright 2017, The Android Open Source Project
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
package com.example.william.my.module.arch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.core.retrofit.callback.RetrofitLiveDataCallback
import com.example.william.my.core.retrofit.exception.ExceptionHandler
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.example.william.my.module.arch.usecase.ArticleUseCase
import io.reactivex.rxjava3.observers.DisposableSingleObserver

class ArticleLiveDataViewModel(
    private val repository: ArticleRepository<ArticleData, ArticleDetailData>
) : ViewModel() {

    private val _articleResponse = MutableLiveData<RetrofitResponse<ArticleData>>()
    val articleResponse: LiveData<RetrofitResponse<ArticleData>>
        get() = _articleResponse

    /**
     * postValue
     */
    fun loadArticle(page: Int) {
        repository.getArticleLiveData(page) {
            _articleResponse.postValue(it)
        }
    }

    /**
     * Single
     */
    fun loadArticle2(page: Int) {
        repository.getArticleSingle(page)
            .subscribe(object : RetrofitLiveDataCallback<ArticleData>() {
                override fun onPostValue(value: RetrofitResponse<ArticleData>?) {
                    super.onPostValue(value)
                    value?.let {
                        _articleResponse.postValue(it)
                    }
                }
            })
    }

    private val articleUseCase: ArticleUseCase = ArticleUseCase(repository)

    /**
     * UseCase
     */
    fun loadArticle3(page: Int) {
        articleUseCase.setPage(page)
        articleUseCase.execute(object :
            DisposableSingleObserver<RetrofitResponse<ArticleData>>() {
            override fun onSuccess(response: RetrofitResponse<ArticleData>) {
                _articleResponse.postValue(response)
            }

            override fun onError(e: Throwable) {
                val exception = ExceptionHandler.handleException(e)
                _articleResponse.postValue(RetrofitResponse.error(exception.message))
            }
        })
    }

    /**
     * 销毁时调用
     */
    override fun onCleared() {
        super.onCleared()
        articleUseCase.clear()
    }
}

