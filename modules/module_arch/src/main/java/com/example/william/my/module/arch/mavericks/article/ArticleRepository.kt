package com.example.william.my.module.arch.mavericks.article

import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.ExperimentalMavericksApi
import com.airbnb.mvrx.MavericksRepository
import com.example.william.my.basic.basic_repository.api.NetworkApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMavericksApi::class)
class ArticleRepository(
    scope: CoroutineScope,
    private val api: NetworkApi,
) : MavericksRepository<ArticleState>(
    initialState = ArticleState(),
    coroutineScope = scope,
    performCorrectnessValidations = BuildConfig.DEBUG,
) {

    fun getArticle(page: Int) {
        suspend {
            api.getArticleSuspend(page)
        }.execute(Dispatchers.IO, retainValue = ArticleState::articleData) {
            copy(articleData = it)
        }
    }
}