package com.example.william.my.module.sample.paging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.paging.rxjava3.cachedIn
import androidx.paging.rxjava3.flowable
import com.example.william.my.basic.basic_repository.api.NetworkApi
import com.example.william.my.basic.basic_repository.bean.Article
import com.example.william.my.basic.basic_repository.data.source.ArticleRepository
import com.example.william.my.basic.basic_repository.database.ArticleDatabase
import com.example.william.my.module.sample.paging.mediator.ArticleRemoteMediator
import com.example.william.my.module.sample.paging.source.ArticlePagingSource
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

class PagingViewModel(
    private val database: ArticleDatabase,
    private val networkApi: NetworkApi,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    /**
     * Paging
     * database
     */
    @OptIn(ExperimentalPagingApi::class)
    val articles: Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                // The pagingSourceFactory lambda should always return a brand new PagingSource
                // when invoked as PagingSource instances are not reusable.
                database.articleDao().getArticlesPagingSource()
            },
            remoteMediator = ArticleRemoteMediator(database, networkApi, articleRepository)
        ).flow.cachedIn(viewModelScope)

    /**
     * Paging
     * .flow
     */
    val articleFlow: Flow<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = 20),
    ) {
        ArticlePagingSource(networkApi, articleRepository)
    }.flow.cachedIn(viewModelScope)

    /**
     * Paging
     * .flowable
     */
    val articleFlowable: Flowable<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        ArticlePagingSource(networkApi, articleRepository)
    }.flowable.cachedIn(viewModelScope)

    /**
     * Paging
     * .liveData
     */
    val articleLiveData: LiveData<PagingData<Article>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        ArticlePagingSource(networkApi, articleRepository)
    }.liveData.cachedIn(viewModelScope)
}