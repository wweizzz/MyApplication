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
package com.example.william.my.module.arch.presenter

import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.module.arch.contract.ArticleContract
import com.example.william.my.module.arch.fragment.MvpFragment


/**
 * Listens to user actions from the UI ([MvpFragment]), retrieves the data and updates the
 * UI as required.
 */
class ArticlePresenter(
    private val repository: ArticleRepository<ArticleData, ArticleDetailData>,
    val view: ArticleContract.View
) : ArticleContract.Presenter {

    override fun loadArticle(page: Int) {
        repository.getArticle(
            page,
            object : ArticleRepository.LoadArticleCallback<ArticleDetailData> {
                override fun onArticleLoaded(articles: List<ArticleDetailData>) {
                    view.showArticle(articles)
                }

                override fun onDataNotAvailable() {
                    view.showNoArticle()
                }
            })
    }

    override fun start() {

    }

    override fun clear() {

    }

    override fun queryData() {

    }
}
