package com.example.william.my.module.room.paging.viewmodel

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
import com.example.william.my.basic.basic_repo.api.ArticleApi
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.basic.basic_repo.bean.ArticleDetailData
import com.example.william.my.basic.basic_repo.data.source.ArticleRepository
import com.example.william.my.basic.basic_repo.database.ArticleDatabase
import com.example.william.my.module.room.paging.mediator.ArticleRemoteMediator
import com.example.william.my.module.room.paging.remotekey.RemoteKeyDatabase
import com.example.william.my.module.room.paging.source.ArticlePagingSource
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

class PagingViewModel(
    private val articleDatabase: ArticleDatabase,
    private val remoteKeyDatabase: RemoteKeyDatabase,
    private val networkApi: ArticleApi,
    private val articleRepository: ArticleRepository<ArticleData, ArticleDetailData>
) : ViewModel() {

    /**
     * Paging
     * database
     */
    @OptIn(ExperimentalPagingApi::class)
    val articles: Flow<PagingData<ArticleDetailData>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                // The pagingSourceFactory lambda should always return a brand new PagingSource
                // when invoked as PagingSource instances are not reusable.
                articleDatabase.articleDao().getArticlesPagingSource()
            },
            remoteMediator = ArticleRemoteMediator(
                articleDatabase,
                remoteKeyDatabase,
                networkApi,
                articleRepository
            )
        ).flow.cachedIn(viewModelScope)

    /**
     * Paging
     * .flow
     */
    val articleFlow: Flow<PagingData<ArticleDetailData>> = Pager(
        config = PagingConfig(pageSize = 20),
    ) {
        ArticlePagingSource(networkApi, articleRepository)
    }.flow.cachedIn(viewModelScope)

    /**
     * Paging
     * .flowable
     */
    val articleFlowable: Flowable<PagingData<ArticleDetailData>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        ArticlePagingSource(networkApi, articleRepository)
    }.flowable.cachedIn(viewModelScope)

    /**
     * Paging
     * .liveData
     */
    val articleLiveData: LiveData<PagingData<ArticleDetailData>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        ArticlePagingSource(networkApi, articleRepository)
    }.liveData.cachedIn(viewModelScope)
}