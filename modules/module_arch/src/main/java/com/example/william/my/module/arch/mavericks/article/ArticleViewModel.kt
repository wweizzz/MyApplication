package com.example.william.my.module.arch.mavericks.article

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.example.william.my.basic.basic_repository.api.ArticleApi
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import kotlinx.coroutines.Dispatchers

class ArticleViewModel(initialState: ArticleState) :
    MavericksViewModel<ArticleState>(initialState) {

    private val api = RetrofitHelper.buildApi(ArticleApi::class.java)

    private val repository: ArticleRepository = ArticleRepository(viewModelScope, api)

    fun loadArticle(page: Int) {
        withState {
            if (it.articleResponse is Loading) return@withState
            suspend {
                api.getArticleSuspend(page)
            }.execute(Dispatchers.IO, retainValue = ArticleState::articleResponse) { state ->
                copy(
                    articleResponse = state,
                )
            }
        }
    }
}