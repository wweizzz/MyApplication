package com.example.william.my.module.arch.mavericks.article

import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.ExperimentalMavericksApi
import com.airbnb.mvrx.MavericksRepository
import com.example.william.my.basic.basic_repo.api.ArticleApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMavericksApi::class)
class ArticleMavericksRepository(
    scope: CoroutineScope,
    private val api: ArticleApi,
) : MavericksRepository<ArticleMavericksState>(
    initialState = ArticleMavericksState(),
    coroutineScope = scope,
    performCorrectnessValidations = BuildConfig.DEBUG,
) {

    fun getArticle(page: Int) {
        suspend {
            api.getArticleSuspend(page)
        }.execute(Dispatchers.IO, retainValue = ArticleMavericksState::articleResponse) {
            copy(articleResponse = it)
        }
    }
}